package controllers;

import models.UserInfo;
import play.mvc.Controller;
import play.mvc.Result;
import play.twirl.api.Html;

import java.sql.Timestamp;
import java.time.Instant;

public class ClientController extends Controller {
  public Result login(String clientId) {
    // Set User status to waiting
    UserInfo user = new UserInfo();
    user.setArrivalTime(Timestamp.from(Instant.now()));
    user.setGallupId("testgallupid");
    user.setLanguage("en");
    user.setRandomizedId("ABCD1234");
    user.setStatus("WAITING");
    user.setSampleGroup("1");
    user.save();

    Html clientTemplate = views.html.client.render(user);
    return ok(clientTemplate);
  }
}