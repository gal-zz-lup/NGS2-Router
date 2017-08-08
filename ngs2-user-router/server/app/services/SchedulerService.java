package services;

import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import play.Logger;
import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.Duration;

public class SchedulerService {
  // This service will query UserInfo at a regular interval and see if there are enough users waiting to fill
  // any active ExperimentInstance

  // Dependency injected
  private final ActorSystem actorSystem;
  private final ExecutionContext executionContext;
  private final Config config;

  @javax.inject.Inject
  public SchedulerService(ActorSystem actorSystem, ExecutionContext executionContext, Config config) {
    this.actorSystem = actorSystem;
    this.executionContext = executionContext;
    this.config = config;

    this.initialize();
  }

  private void initialize() {
    this.actorSystem.scheduler().schedule(
        Duration.fromNanos(config.getDuration("peel.server.scheduleInterval").toNanos()),
        Duration.fromNanos(config.getDuration("peel.server.scheduleInterval").toNanos()),
        () -> Logger.debug("SchedulerTimerTask.run()"),
        this.executionContext
    );
  }

}

