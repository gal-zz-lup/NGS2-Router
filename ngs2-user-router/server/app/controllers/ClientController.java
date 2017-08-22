package controllers;

import actors.QueueActorProtocol;
import akka.actor.ActorRef;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.typesafe.config.Config;
import models.UserInfo;
import play.mvc.Controller;
import play.mvc.Result;
import scala.compat.java8.FutureConverters;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import static akka.pattern.Patterns.ask;

public class ClientController extends Controller {

  // Dependency injected
  private final ActorRef queueActor;
  private final Config config;

  @Inject
  public ClientController(@Named("peel-queue-actor") ActorRef queueActor, Config config) {
    this.queueActor = queueActor;
    this.config = config;
  }

  /**
   * Check the validity of the user as request arrive and save user information to UserInfo.
   * @param clientId
   * @return
   */
  public Result login(String clientId) {
    UserInfo user = UserInfo.find.query().where().eq("randomized_id", clientId).findUnique();
    if (user == null) {
      return notFound("User not found.");
    }
    // User is connecting to the router, set their status to "WAITING" and
    // their arrival time to now
    if (!user.getStatus().equals("PLAYING")) {
      user.setStatus("WAITING");
      user.setArrivalTime(Timestamp.from(Instant.now()));
      // We need to set the lastCheckInTime
      user.setLastCheckIn(user.getArrivalTime());
      user.save();
    }
    return ok(views.html.client.render(user));
  }

  /**
   * Asking the queue to be update based on actor timeout setting.
   * @param clientId
   * @return
   */
  public CompletionStage<Result> update(String clientId) {
    return FutureConverters.toJava(
        ask(queueActor,
            new QueueActorProtocol.ClientUpdate(clientId),
            config.getDuration("peel.server.actorTimeout", TimeUnit.MILLISECONDS)))
        .thenApply(response -> ok((ObjectNode) response));
  }

  public Result waiting(String clientId) {
    UserInfo user = UserInfo.find.query().where().eq("randomized_id", clientId).findUnique();
    if (user == null) {
      return notFound("User not found.");
    }
    return ok(views.html.waiting.render(user));
  }

  public Result noExperiments(String clientId) {
    UserInfo user = UserInfo.find.query().where().eq("randomized_id", clientId).findUnique();
    if (user == null) {
      return notFound("User not found.");
    }
    return ok(views.html.no_experiments.render(user));
  }
}