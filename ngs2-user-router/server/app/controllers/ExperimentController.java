package controllers;

import com.avaje.ebean.Model.*;
import com.fasterxml.jackson.databind.JsonNode;
import models.Experiment;
import play.data.Form;
import play.data.FormFactory;
import play.data.validation.Constraints;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import scala.util.parsing.json.JSONObject;
import util.Utility;

import javax.inject.Inject;
import java.sql.Timestamp;


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
        experiment.setActualURL(experimentForm.get().experimentName);
        experiment.setShortenURL(experiment.getActualURL()); //need to implement url shortner
        experiment.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        experiment.setNumberOfParticipants(experimentForm.get().numberOfParticipants);
        experiment.setStatus(experimentForm.get().status);
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

        Experiment experiment;
        Form<Experiment> experimentForm;

        try {
            experiment = Experiment.find.byId(id);
            experimentForm = formFactory.form(Experiment.class).fill(experiment);
            experiment = experimentForm.get();
            experiment.save();
            return ok("Experiment successfully updated");
        } catch (Exception ex) {
            return badRequest("Something went wrong during update");
        }
    }

    /**
     * Delete experiment
     * @return
     */
    public Result deleteExperiment(Long id) {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest(Utility.createResponse("Expecting json", false));
        }
        else {
            Experiment.find.ref(id).delete();
        }

        return ok("Experiment successfully deleted.");
    }

    public static class ExperimentForm {

        @Constraints.Required
        @Constraints.MaxLength(255)
        public String experimentName;

        @Constraints.MaxLength(255)
        @Constraints.Required
        public String actualURL;

        @Constraints.MaxLength(255)
        @Constraints.Required
        public String shortenURL;

        @Constraints.Required
        public int numberOfParticipants;

        @Constraints.Required
        public String status;

    }


}