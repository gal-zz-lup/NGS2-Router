package actors;

public class QueueActorProtocol {
  public static class Tick {
  }

  public static class ClientUpdate {
    public final String clientId;

    public ClientUpdate(String clientId) {
      this.clientId = clientId;
    }
  }
}
