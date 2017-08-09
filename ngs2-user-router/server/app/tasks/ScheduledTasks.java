package tasks;

import actors.QueueActorProtocol;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.Duration;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.concurrent.TimeUnit;

@Singleton
public class ScheduledTasks {
  // Dependency injected
  private final ActorRef queueActor;
  private final ActorSystem actorSystem;
  private final ExecutionContext executionContext;
  private final Config config;

  @Inject
  public ScheduledTasks(@Named("peel-queue-actor") ActorRef queueActor, ActorSystem actorSystem, ExecutionContext executionContext, Config config) {
    this.queueActor = queueActor;
    this.actorSystem = actorSystem;
    this.executionContext = executionContext;
    this.config = config;

    this.initialize();
  }

  private void initialize() {
    this.actorSystem.scheduler().schedule(
        Duration.fromNanos(config.getDuration("peel.server.scheduleInterval", TimeUnit.NANOSECONDS)),
        Duration.fromNanos(config.getDuration("peel.server.scheduleInterval", TimeUnit.NANOSECONDS)),
        queueActor,
        new QueueActorProtocol.Tick(),
        executionContext,
        ActorRef.noSender()
    );
  }
}
