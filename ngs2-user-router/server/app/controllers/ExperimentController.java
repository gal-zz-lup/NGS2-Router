package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Experiment;
import play.data.Form;
import play.data.FormFactory;
import play.data.validation.Constraints;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import util.Utility;

import javax.inject.Inject;


/**
 * Created by anuradha_uduwage.
 */
public class ExperimentController extends Controller {

    @Inject
    FormFactory formFactory;

    /**
     * Get all experiments
     * @return return list of experiments.
     */
    public Result getAllExperiments() {

        return ok(Json.toJson(Experiment.find.all()));
    }

    /**
     * Create experiment.
     * @return
     */
    public Result createExperiment() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest(Utility.createResponse("Expecting json", false));
        }

        Form<ExperimentForm> experimentForm = formFactory.form(ExperimentForm.class).bindFromRequest();
        Experiment experiment = new Experiment();
        experiment.experimentName = experimentForm.get().experimentName;
        experiment.save();
        JsonNode jsonObject = Json.toJson(experiment);
        return created(Utility.createResponse(jsonObject, true));
    }

    /**
     * Update experiment
     * @param id experiment id
     * @return
     */
    public Result updateExperiment(Long id) {
        Experiment experiment = Experiment.find.byId(id);
        Form<ExperimentForm> experimentForm = formFactory.form(ExperimentForm.class).bindFromRequest();
        experiment.experimentName = experimentForm.get().experimentName;
        experiment.update();
        JsonNode jsonObject = Json.toJson(experiment);
        return ok(Utility.createResponse(jsonObject, true));
    }

    /**
     * Delete experiment
     * @return
     */
    public Result deleteExperiment(Long id) {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest(Utility.createResponse("Expecting json", false));
        } else {
            boolean status = Experiment.find.ref(id).delete();
            if (!status) {
                return notFound(Utility.createResponse(
                        "Experiment with id:" + id + " not found", false));
            } else {
                return ok(Utility.createResponse(
                        "Experiment with id:" + id + " deleted", true));
            }
        }
    }

    /**
     * Static class to hold experiment form values.
     */
    public static class ExperimentForm {
        @Constraints.Required
        @Constraints.MaxLength(255)
        public String experimentName;
    }


}