package controllers;

import org.apache.commons.lang3.StringUtils;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import services.UserSessionWrapper;

/**
 * Created by anuradha_uduwage.
 */
public class SecurityController extends Security.Authenticator {

    private final UserSessionWrapper userSessionWrapper;

    public SecurityController() {

        userSessionWrapper = UserSessionWrapper.instance;

    }

    @Override
    public String getUsername(Http.Context context) {
        final String adminEmail = context.session().get("user");

        if (StringUtils.isNotBlank(adminEmail) && userSessionWrapper.isLoggedIn(adminEmail)) {
            return adminEmail;
        } else {
            return null;
        }
    }

    @Override
    public Result onUnauthorized(Http.Context context) {
        // wonder if we should re-direct to index page.
        return redirect("/login");
    }


}
