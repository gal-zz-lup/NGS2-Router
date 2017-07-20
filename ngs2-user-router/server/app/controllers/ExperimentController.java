package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Experiment;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import util.Utility;

/**
 * Created by anuradha_uduwage.
 */
public class ExperimentController extends Controller {

    public Result getAllExperiments() {
        return ok(Json.toJson(Experiment.find.all()));
    }

    public Result createExperiment() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest(Utility.createResponse("Expecting json", false));
        }
        Experiment experiment = new Experiment();
        // set all the fields
        JsonNode jsonObject = Json.toJson(experiment);
        return created(Utility.createResponse(jsonObject, true));
    }

    public Result updateExperiment(Experiment experiment) {
        return ok("TODO");
    }

    public Result deleteExperiment() {
        return ok("TODO");
    }
}
