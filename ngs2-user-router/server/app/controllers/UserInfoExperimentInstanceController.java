package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Experiment;
import models.UserInfoExperimentInstance;
import models.UserInfo;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import util.Utility;

import java.util.List;

/**
 * Created by anuradha_uduwage.
 */
public class UserInfoExperimentInstanceController extends Controller {

    /**
     * Given userId method will return all the experiments that user is participating or participated in past.
     * @param userId userId
     * @return
     */
    public Result experimentsByUser(Long userId) {

        //Experiment experiment = UserInfoExperimentInstance.find.ref(userId).getExperimentInstance();
        //JsonNode jsonObject = Json.toJson(experiment);
        //return created(Utility.createResponse(jsonObject, true));
        return null;
    }

    /**
     * Given an exeprimentId method will return all the users who in that experiment;
     * @param experimentId
     * @return
     */
    public Result usersByExperiment(Long experimentId) {

        UserInfo userInfo = UserInfoExperimentInstance.find.ref(experimentId).getUserInfo();
        JsonNode jsonObject = Json.toJson(userInfo);
        return created(Utility.createResponse(jsonObject, true));
    }
}
