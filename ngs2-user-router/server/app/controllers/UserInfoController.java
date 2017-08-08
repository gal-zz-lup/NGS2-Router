package controllers;

import models.UserInfo;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Created by anuradha_uduwage.
 */
@Security.Authenticated(SecurityController.class)
public class UserInfoController extends Controller {

    /**
     * Return all the users
     * @return
     */
    public Result getAllUsers() {
        return ok(Json.toJson(UserInfo.find.all()));
    }
}
