import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Result;
import play.test.WithApplication;
import util.DemoData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static play.test.Helpers.*;

/**
 * Created by anuradha_uduwage on 7/13/17.
 */
public class AuthenticationControllerTest extends WithApplication {

    @Test
    public void login() {
        DemoData demoData = app.injector().instanceOf(DemoData.class);
        ObjectNode loginJson = Json.newObject();
        loginJson.put("email", demoData.admin1.getEmail());
        loginJson.put("password", demoData.admin1.getPassword());

        Result result = route(fakeRequest(controllers.routes.AuthenticationController.login()).bodyJson(loginJson));

        assertEquals(OK, result.status());

    }
}
