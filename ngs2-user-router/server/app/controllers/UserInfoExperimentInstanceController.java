package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.UserInfo;
import models.UserInfoExperimentInstance;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import util.Utility;

/**
 * Created by anuradha_uduwage.
 */
@Security.Authenticated(SecurityController.class)
public class UserInfoExperimentInstanceController extends Controller {

  /**
   * Given userId method will return all the experiments that user is participating or participated in past.
   *
   * @param userId userId
   * @return
   */
  public Result experimentsByUser(Long userId) {

    //List<Experiment> experiments = UserInfoExperimentInstance.find.ref(userId).getExperiment();
    //JsonNode jsonObject = Json.toJson(experiment);
    //return created(Utility.createResponse(jsonObject, true));
    return null;
  }

  /**
   * Given an experimentId method will return all the users who in that experiment;
   *
   * @param experimentId
   * @return
   */
  public Result usersByExperiment(Long experimentId) {

    UserInfo userInfo = UserInfoExperimentInstance.find.ref(experimentId).getUserInfo();
    JsonNode jsonObject = Json.toJson(userInfo);
    return created(Utility.createResponse(jsonObject, true));
  }
}
