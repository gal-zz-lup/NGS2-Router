package tasks;

import com.google.inject.AbstractModule;

public class ScheduledTasksModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(ScheduledTasks.class).asEagerSingleton();
  }
}
