package controllers;

import models.UserInfo;
import play.mvc.Controller;
import play.mvc.Result;
import play.twirl.api.Html;

public class ClientController extends Controller {
  public Result login(String clientId) {
    UserInfo user = UserInfo.find.where().eq("randomized_id", clientId).findUnique();
    if (user == null) {
      return notFound("User not found.");
    }
    Html clientTemplate = views.html.client.render(user);
    return ok(clientTemplate);
  }
}