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
import util.Utility;

import javax.inject.Inject;
import java.sql.Timestamp;

import services.URLShortenerService;


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
        URLShortenerService urlShortenerService = new URLShortenerService();

        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest(Utility.createResponse("Expecting json", false));
        }

        Form<ExperimentForm> experimentForm = formFactory.form(ExperimentForm.class).bindFromRequest();
        Experiment experiment = new Experiment();
        experiment.setExperimentName(experimentForm.get().experimentName);
        experiment.setActualURL(experimentForm.get().actualURL);
        //TODO: Revisit URL Shortner to see if we need a database call to check if URL exist
        //TODO: instead of hashmap. At the moment if Experiment Controller gets call at multiple times
        //TODO: we will be looking at empty hashmap.
        //TODO: ShortURL should be created server-side, not passed in from the client.
        experiment.setShortenURL("TODO");
        experiment.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        experiment.setNumberOfParticipants(experimentForm.get().numberOfParticipants);
        experiment.setPriority(experimentForm.get().priority);
        experiment.setStatus("ACTIVE");
        //experimentForm.get(); can we use this instead of using setters?
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
            JsonNode jsonObject = Json.toJson(experiment);
            return ok(Utility.createResponse(jsonObject, true));
        } catch (Exception ex) {
            return notFound("Something went wrong during update");
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

        @Constraints.MaxLength(255)
        @Constraints.Required
        public String actualURL;

        @Constraints.Required
        public int numberOfParticipants;

        @Constraints.Required
        public int priority;

    }


}