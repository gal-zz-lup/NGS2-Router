package controllers;

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


}
