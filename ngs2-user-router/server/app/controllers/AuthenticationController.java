package controllers;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import services.UserSessionWrapper;

/**
 * Created by anuradha_uduwage.
 */
public class AuthenticationController extends Controller{

    private final UserSessionWrapper userSessionWrapper;

    public AuthenticationController() {
        userSessionWrapper = UserSessionWrapper.instance;
    }

    public Result authenticateAdmin() {
        //final final Form<Login> loginForm = formFactory.form(Login.class).bindFromRequest();>
        return ok("TODO: need to implement");
    }
}
