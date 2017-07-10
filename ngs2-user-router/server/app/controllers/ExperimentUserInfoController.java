package controllers;

import play.mvc.Controller;
import play.mvc.Result;

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


        return ok("");
    }

    /**
     * Given an exeprimentId method will return all the users who in that experiment;
     * @param experimentId
     * @return
     */
    public Result usersByExperiment(Long experimentId) {
        return ok("");
    }
}
