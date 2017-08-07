package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import models.UserInfo;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.twirl.api.Html;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;

public class ClientController extends Controller {
  // TODO: Replace this with the SchedulerService
  private int TEMPMAXVALUE = 3;
  private int TEMPMINVALUE = 0;
  private HashMap<String, Timestamp> joinedClients = new HashMap<>();

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
    // TODO: validate clientId efficiently before returning result
    ObjectNode result = Json.newObject();
    result.put("clientId", clientId);
    joinedClients.put(clientId, Timestamp.from(Instant.now()));
    if (joinedClients.size() >= TEMPMAXVALUE) {
      UserInfo user = UserInfo.find.where().eq("randomized_id", clientId).findUnique();
      user.setStatus("PLAYING");
      result.put("src", "http://brdbrd.net");
    }
    ObjectNode progress = Json.newObject();
    progress.put("valuemax", TEMPMAXVALUE);
    progress.put("valuemin", TEMPMINVALUE);
    progress.put("value", joinedClients.size());
    result.put("progress", progress);
    return ok(result);
  }

  public Result waiting(String clientId) {
    UserInfo user = UserInfo.find.where().eq("randomized_id", clientId).findUnique();
    if (user == null) {
      // TODO: Redirect to 401 page here
      return notFound("User not found.");
    }
    Html clientTemplate = views.html.waiting.render(user);
    return ok(clientTemplate);
  }
}