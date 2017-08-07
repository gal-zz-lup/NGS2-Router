package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import models.UserInfo;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.twirl.api.Html;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Random;

public class ClientController extends Controller {
  private int valueTemp = 0;
  private int TEMPMAXVALUE = 20;
  private int TEMPMINVALUE = 0;

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

  public Result update(String clientId) {
    ObjectNode result = Json.newObject();
    result.put("clientId", clientId);
    // TODO: validate clientId efficiently before returning result
    Random random = new Random();
    switch (random.nextInt(3)) {
      case 0: result.put("src", "http://brdbrd.net");
      break;
      case 1: result.put("src", "http://breadboard.yale.edu");
      break;
      case 2: result.put("src", "http://xkcd.com");
      break;
    }
    ObjectNode progress = Json.newObject();
    progress.put("valuemax", TEMPMAXVALUE);
    progress.put("valuemin", TEMPMINVALUE);
    if (valueTemp < TEMPMAXVALUE) valueTemp++;
    progress.put("value", valueTemp);
    result.put("progress", progress);
    return ok(result);
  }
}