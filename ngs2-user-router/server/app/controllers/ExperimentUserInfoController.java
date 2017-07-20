package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Experiment;
import models.ExperimentUserInfo;
import models.UserInfo;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import util.Utility;

import java.util.List;

/**
 * Created by anuradha_uduwage.
 */
public class ExperimentUserInfoController extends Controller {

    /**
     * Given userId method will return all the experiments that user is participating or participated in past.
     * @param userId userId
     * @return
     */
    public Result experimentsByUser(Long userId) {

        List<Experiment> experimentsList = ExperimentUserInfo.find.ref(userId).getExperiments();
        JsonNode jsonObject = Json.toJson(experimentsList);
        return created(Utility.createResponse(jsonObject, true));
    }

    /**
     * Given an exeprimentId method will return all the users who in that experiment;
     * @param experimentId
     * @return
     */
    public Result usersByExperiment(Long experimentId) {

        List<UserInfo> userInfoList = ExperimentUserInfo.find.ref(experimentId).getUsers();
        JsonNode jsonObject = Json.toJson(userInfoList);
        return created(Utility.createResponse(jsonObject, true));
    }
}
