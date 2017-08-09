package tasks;

import actors.QueueActor;
import com.google.inject.AbstractModule;
import play.libs.akka.AkkaGuiceSupport;

public class ScheduledTasksModule extends AbstractModule implements AkkaGuiceSupport {

  @Override
  protected void configure() {
    bindActor(QueueActor.class, "peel-queue-actor");
    bind(ScheduledTasks.class).asEagerSingleton();
  }
}
