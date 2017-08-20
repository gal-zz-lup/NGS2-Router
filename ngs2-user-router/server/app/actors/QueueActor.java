package actors;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.typesafe.config.Config;
import io.ebean.*;
import models.Experiment;
import models.ExperimentInstance;
import models.UserInfo;
import actors.QueueActorProtocol.*;
import models.UserInfoExperimentInstance;
import play.Logger;
import play.db.ebean.EbeanConfig;
import play.libs.Json;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class QueueActor extends UntypedAbstractActor {

  public static Props getProps() {
    return Props.create(QueueActor.class);
  }

  private Integer minValue = 0;
  private Integer maxValue = 0;
  private Integer waitingClients = 0;

  private final Config config;
  private final EbeanConfig ebeanConfig;

  private final EbeanServer ebeanServer;

  @Inject
  public QueueActor(Config config, EbeanConfig ebeanConfig) {
    this.config = config;
    this.ebeanConfig = ebeanConfig;
    this.ebeanServer = Ebean.getServer(ebeanConfig.defaultServer());
  }

  private void updateMaxValue() {
    List<ExperimentInstance> activeExperimentInstances = ebeanServer.find(
            ExperimentInstance.class).where().eq("status", "ACTIVE").findList();
    for (ExperimentInstance activeExperimentInstance : activeExperimentInstances) {
      maxValue = Math.max(maxValue, activeExperimentInstance.nParticipants);
    }
  }

  private long getTimeDifference(Timestamp arrival, Timestamp current) {
    long arrivalMillisecond = arrival.getTime();
    long currentMillisecond = current.getTime();
    long timeDiff = currentMillisecond - arrivalMillisecond;
    long diffInMinutes = timeDiff / 1000;
    return diffInMinutes;
  }

  @Override
  public void onReceive(Object message) throws Throwable {

    //List of users who are ready to be assigned

    if (message instanceof Tick) {
      //Logger.debug("Tick");
      updateMaxValue();

      List<UserInfo> waitingUsers = UserInfo.find.query().where()
              .eq("status", "WAITING").setOrderBy("arrival_time asc")
              .fetch("experimentInstanceList").findList();

      // Update number of waiting clients here
      waitingClients = waitingUsers.size();

      List<ExperimentInstance> activeExperimentInstances = ExperimentInstance.find.query().where()
              .eq("status", "ACTIVE").setOrderBy("priority asc").findList();

      //shuffling the active experiment list
      Collections.shuffle(activeExperimentInstances);

      Logger.debug(waitingUsers.size() + " players are WAITING");
      if (waitingUsers.size() > 0) {
        Iterator<UserInfo> iter = waitingUsers.iterator();
        while(iter.hasNext()) {
          UserInfo user = iter.next();
          //Logger.debug(user.getRandomizedId() + ": " + user.getArrivalTime() + " : " + user.getLastCheckIn());
          // if user has just arrived lastCheckIn is same as arrival
          long waitedTime;
          if (user.getLastCheckIn() != null) {
            waitedTime = getTimeDifference(user.getLastCheckIn(), Timestamp.from(Instant.now()));
          } else {
            waitedTime = getTimeDifference(user.getArrivalTime(), Timestamp.from(Instant.now()));
          }
          if (waitedTime > config.getDuration("peel.server.idleTime", TimeUnit.SECONDS)) {
            Logger.debug("User " + user.getRandomizedId() + " is IDLE");
            user.setStatus("IDLE");
            user.save();
            iter.remove();
          }
        }
        outter:
        for (UserInfo userInfo : waitingUsers) {
          innerloop:
          for (ExperimentInstance experimentInstance : activeExperimentInstances) {

            Logger.debug("Evaluating User " + userInfo.getUserId() + ":" + userInfo.getRandomizedId() + experimentInstance.id);

            // Since we are using many to many relationship we can get the userInfo list with experimentInstance
            if (userInfo.hasParticipatedInExperiment(experimentInstance.getExperiment())) {
              Logger.debug("User " + userInfo.getRandomizedId() + " has already participated in this experiment");
              break innerloop;
            }

            //using the lambda function to filter and collect the users who have not participated in the instance.
            // This should filter users who have not participated in the parent Experiment, not the ExperimentInstance
            List<UserInfo> filteredByInstanceWaitingUsers = waitingUsers.stream().filter(u -> !u.hasParticipatedInExperiment(
                    experimentInstance.experiment)).collect(Collectors.toList());
            Logger.debug(filteredByInstanceWaitingUsers.size() + " users have not participated in experiment ");

            if(filteredByInstanceWaitingUsers.size() >= experimentInstance.getnParticipants()) {
              Logger.debug("experimentInstance.getnParticipants() = " + experimentInstance.getnParticipants());
              experimentInstance.assignUserInfo(filteredByInstanceWaitingUsers
                      .subList(0, experimentInstance.getnParticipants()));
              Logger.debug("Router send of users :: " + filteredByInstanceWaitingUsers.stream().map(u ->
                      u.getRandomizedId())
                      .collect(Collectors
                              .joining("|")) + "to experiment ::" + experimentInstance.getExperimentInstanceName());
              //waitingUsers = waitingUsers.stream().filter(u -> !filteredByInstanceWaitingUsers.contains(u.getRandomizedId())).collect(Collectors.toList());
              waitingUsers.removeAll(filteredByInstanceWaitingUsers);
              Logger.debug(waitingUsers.size() + "waiting");
              if (waitingUsers.size() > 0)
                break innerloop;
              else
                break outter;
            }
          }
        }
      }
    }

    if (message instanceof ClientUpdate) {
      ClientUpdate clientUpdate = (ClientUpdate) message;

      UserInfo user = ebeanServer.find(UserInfo.class).where().eq("randomized_id", clientUpdate.clientId).findUnique();
      if (user != null) {
        // Update last_check_in timestamp
        user.setLastCheckIn(Timestamp.from(Instant.now()));
        user.save();
        ObjectNode result = Json.newObject();
        result.put("status", user.getStatus());
        result.put("src", user.getCurrentGameUrl());
        ObjectNode progress = Json.newObject();
        progress.put("valuemax", maxValue);
        progress.put("valuemin", minValue);
        progress.put("value", waitingClients);
        result.set("progress", progress);
        sender().tell(result, self());
      }
    }
  }


}
