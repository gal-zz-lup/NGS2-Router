package actors;

import akka.actor.Props;
import akka.actor.UntypedActor;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;
import com.avaje.ebean.RawSql;
import com.avaje.ebean.RawSqlBuilder;
import com.avaje.ebean.annotation.Sql;
import models.Experiment;
import models.ExperimentInstance;
import models.UserInfo;
import actors.QueueActorProtocol.*;
import play.Configuration;
import play.Logger;

import javax.inject.Inject;
import javax.persistence.Entity;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;

public class QueueActor extends UntypedActor {

  public static Props props = Props.create(QueueActor.class);

  // This is an in-memory representation of waiting clients for quickly responding to client polling
  private HashMap<String, Timestamp> waitingClients = new HashMap<>();

  private Integer minValue = 0;
  private Integer maxValue = 0;

  private final Configuration configuration;

  @Inject
  public QueueActor(Configuration configuration) {
    this.configuration = configuration;
    updateMaxValue();
  }

  @Entity
  @Sql
  private class MaxValueResult {
    Integer value;
  }

  private void updateMaxValue() {
    String sql = "select max(n_participants) as value from experiment_instance where status = 'ACTIVE';";
    RawSql rawSql = RawSqlBuilder.parse(sql).columnMapping("value", "value").create();
    Query<MaxValueResult> query = Ebean.find(MaxValueResult.class);
    MaxValueResult maxValueResult = query.setRawSql(rawSql).findUnique();
    maxValue = maxValueResult.value;
  }

  @Override
  public void onReceive(Object message) throws Throwable {
    if (message instanceof Tick) {
      List<UserInfo> waitingUsers = UserInfo.find.where().eq("status", "WAITING").setOrderBy("arrival_time asc").findList();
      List<ExperimentInstance> activeExperimentInstances = ExperimentInstance.find.where().eq("status", "ACTIVE").setOrderBy("priority asc").findList();

      if (waitingUsers.size() > 0) {
        for(UserInfo user : waitingUsers) {
          Logger.debug(user.getRandomizedId() + ": " + user.getArrivalTime());
        }
      }
    }

    if (message instanceof ClientUpdate) {
      ClientUpdate clientUpdate = (ClientUpdate) message;
      // Update last updated timestamp
      waitingClients.put(clientUpdate.clientId, Timestamp.from(Instant.now()));

      ProgressUpdate progressUpdate = new ProgressUpdate(minValue, maxValue, waitingClients.size(), "");

      sender().tell(progressUpdate, self());
    }
  }


}
