package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.*;
import models.UserInfo;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.List;

/**
 * Created by anuradha_uduwage.
 */
@Security.Authenticated(SecurityController.class)
public class UserInfoController extends Controller {

  /**
   * Return all the users
   *
   * @return A Result with a json representation of an Array of users.
   */
  public Result getAllUsers() {
    return ok(Json.toJson(UserInfo.find.all()));
  }

  /**
   * Return a CSV of the gallup_id and client URL
   *
   * @return A Result with a json including a CSV string to display
   */
  public Result exportUsers() {
    String lineEnding = "\n";
    String csvString = "gallup_id,url" + lineEnding;
    List<UserInfo> users = UserInfo.find.all();
    for (UserInfo user : users) {
      String clientUrl = controllers.routes.ClientController.login(user.getRandomizedId()).absoluteURL(request());
      csvString += user.getGallupId() + "," + clientUrl + lineEnding;
    }
    ObjectNode returnJson = Json.newObject();
    returnJson.put("user_csv", csvString);

    return ok(returnJson);
  }

}
