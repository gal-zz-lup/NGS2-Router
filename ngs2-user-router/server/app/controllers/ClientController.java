package controllers;

import models.UserInfo;
import play.mvc.Controller;
import play.mvc.Result;
import play.twirl.api.Html;

import java.sql.Timestamp;
import java.time.Instant;

public class ClientController extends Controller {
  public Result login(String clientId) {
    UserInfo user = UserInfo.find.where().eq("randomized_id", clientId).findUnique();
    if (user == null) {
      // TODO: Redirect to 401 page here
      return notFound("User not found.");
    }
    // User is connecting to the router, set their status to "WAITING" and
    // their arrival time to now
    user.setStatus("WAITING");
    user.setArrivalTime(Timestamp.from(Instant.now()));
    user.save();
    Html clientTemplate = views.html.client.render(user);
    return ok(clientTemplate);
  }
}