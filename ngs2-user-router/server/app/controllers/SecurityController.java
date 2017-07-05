package controllers;

import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Created by anuradha_uduwage.
 */
public class SecurityController extends Security.Authenticator {


    @Override
    public String getUsername(Http.Context context) {
        return context.session().get("username");
    }

    @Override
    public Result onUnauthorized(Http.Context context) {
        // wonder if we should re-direct to index page.
        return unauthorized();
    }


}
