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

  public static class ProgressUpdate {
    public final Integer minValue;
    public final Integer maxValue;
    public final Integer value;
    public final String src;

    public ProgressUpdate(Integer minValue, Integer maxValue, Integer value, String src) {
      this.minValue = minValue;
      this.maxValue = maxValue;
      this.value = value;
      this.src = src;
    }
  }
}
