package actors;

import akka.actor.Props;
import akka.actor.UntypedActor;
import play.Logger;

public class QueueActor extends UntypedActor {

  public static Props props = Props.create(QueueActor.class);

  @Override
  public void onReceive(Object message) throws Throwable {
    if (message instanceof QueueActorProtocol.Tick) {
      Logger.debug("QueueActor.onReceive()");
    }
  }
}
