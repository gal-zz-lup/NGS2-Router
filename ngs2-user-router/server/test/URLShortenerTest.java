import org.junit.Test;
import services.URLShortenerService;

/**
 * Created by anuradha_uduwage on 7/29/17.
 */
public class URLShortenerTest {

  @Test
  public void getShorURLTest() {

    URLShortenerService urlShortenerService = new URLShortenerService();
    String test = "http://www.facebook.com/auduwage/234234234/test";
    String shortURL = urlShortenerService.getShortURL(test);
    System.out.print(shortURL);
  }
}
