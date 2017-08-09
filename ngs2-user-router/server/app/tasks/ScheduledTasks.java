package tasks;

import actors.QueueActor;
import actors.QueueActorProtocol;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import scala.concurrent.duration.Duration;
import play.Configuration;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ScheduledTasks {
  // Dependency injected
  private final ActorSystem actorSystem;
  private final Configuration configuration;

  private final ActorRef queueActor;

  @Inject
  public ScheduledTasks(ActorSystem actorSystem, Configuration configuration) {
    this.actorSystem = actorSystem;
    this.configuration = configuration;

    this.queueActor = actorSystem.actorOf(QueueActor.props);

    this.initialize();
  }

  private void initialize() {
    this.actorSystem.scheduler().schedule(
        Duration.fromNanos(configuration.getNanoseconds("peel.server.scheduleInterval")),
        Duration.fromNanos(configuration.getNanoseconds("peel.server.scheduleInterval")),
        queueActor,
        new QueueActorProtocol.Tick(),
        actorSystem.dispatcher(),
        ActorRef.noSender()
    );
  }
}
