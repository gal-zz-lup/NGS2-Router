package actors;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.typesafe.config.Config;
import io.ebean.*;
import models.ExperimentInstance;
import models.UserInfo;
import actors.QueueActorProtocol.*;
import play.Logger;
import play.db.ebean.EbeanConfig;
import play.libs.Json;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;

public class QueueActor extends UntypedAbstractActor {

  public static Props getProps() {
    return Props.create(QueueActor.class);
  }

  // This is an in-memory representation of waiting clients for quickly responding to client polling
  private HashMap<String, Timestamp> waitingClients = new HashMap<>();

  private Integer minValue = 0;
  private Integer maxValue = 0;

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
    List<ExperimentInstance> activeExperimentInstances = ebeanServer.find(ExperimentInstance.class).where().eq("status", "ACTIVE").findList();
    for (ExperimentInstance activeExperimentInstance : activeExperimentInstances) {
      maxValue = Math.max(maxValue, activeExperimentInstance.nParticipants);
    }
  }

  @Override
  public void onReceive(Object message) throws Throwable {
    if (message instanceof Tick) {
      //Logger.debug("Tick");
      updateMaxValue();

      List<UserInfo> waitingUsers = UserInfo.find.query().where().eq("status", "WAITING").setOrderBy("arrival_time asc").findList();
      List<ExperimentInstance> activeExperimentInstances = ExperimentInstance.find.query().where().eq("status", "ACTIVE").setOrderBy("priority asc").findList();

      if (waitingUsers.size() > 0) {
        for(UserInfo user : waitingUsers) {
          //Logger.debug(user.getRandomizedId() + ": " + user.getArrivalTime());
        }
      }
    }

    if (message instanceof ClientUpdate) {
      ClientUpdate clientUpdate = (ClientUpdate) message;
      // Update last updated timestamp
      waitingClients.put(clientUpdate.clientId, Timestamp.from(Instant.now()));

      ObjectNode result = Json.newObject();
      ObjectNode progress = Json.newObject();
      progress.put("valuemax", maxValue);
      progress.put("valuemin", minValue);
      progress.put("value", waitingClients.size());
      result.put("progress", progress);

      sender().tell(result, self());
    }
  }


}
