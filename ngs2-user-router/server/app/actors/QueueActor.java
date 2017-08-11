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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
              .eq("status", "WAITING").setOrderBy("arrival_time asc").findList();

      // Update number of waiting clients here
      waitingClients = waitingUsers.size();

      List<ExperimentInstance> activeExperimentInstances = ExperimentInstance.find.query().where()
              .eq("status", "ACTIVE").setOrderBy("priority asc").findList();

      if (waitingUsers.size() > 0) {
        Iterator<UserInfo> iter = waitingUsers.iterator();
        while(iter.hasNext()) {
          UserInfo user = iter.next();
          //Logger.debug(user.getRandomizedId() + ": " + user.getArrivalTime());
          long waitedTime = getTimeDifference(user.getArrivalTime(), Timestamp.from(Instant.now()));
          if (waitedTime > config.getDuration("peel.server.idleTime", TimeUnit.SECONDS)) {
            user.setStatus("IDLE");
            user.save();
            iter.remove();
          }
        }
      }

      if (waitingUsers.size() > 0) {
        for (UserInfo userInfo : waitingUsers) {
          for (ExperimentInstance experimentInstance : activeExperimentInstances) {
            // get the users by experimentInstance
            List<UserInfoExperimentInstance> userInfoExperimentInstances = UserInfoExperimentInstance.
                    getUsersInfoExperimentByInstanceId(experimentInstance.id);
            // we need to iterate over userinfo_experiment_instance table to get users in instances
            for (UserInfoExperimentInstance uInfoExpInstance : userInfoExperimentInstances) {
              if (userInfo.getUserId() == uInfoExpInstance.getUserInfo().getUserId()) {
                break;
              } else {
                if(waitingClients > experimentInstance.getnParticipants()) {
                  experimentInstance.assignUserInfo(userInfo);
                  break;
                }
              }
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
        ObjectNode progress = Json.newObject();
        progress.put("valuemax", maxValue);
        progress.put("valuemin", minValue);
        progress.put("value", waitingClients);
        result.put("progress", progress);
        sender().tell(result, self());
      }
    }
  }


}
