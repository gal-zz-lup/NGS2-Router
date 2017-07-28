package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Experiment;
import models.ExperimentInstance;
import play.data.Form;
import play.data.FormFactory;
import play.data.validation.Constraints;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.URLShortenerService;
import util.Utility;

import javax.inject.Inject;
import java.sql.Timestamp;


/**
 * Created by anuradha_uduwage.
 */
public class ExperimentInstanceController extends Controller {

    @Inject
    FormFactory formFactory;

    /**
     * Get all experiment instances
     * @return return list of experiment instances.
     */
    public Result getAllExperimentInstances() {

        return ok(Json.toJson(ExperimentInstance.find.all()));
    }

    /**
     * Get all experiment instances of a certain experiment type
     * @return return list of experiment instances.
     */
    public Result getAllExperimentInstancesByExperiment(long experimentId) {
        return ok(Json.toJson(ExperimentInstance.find.where().eq("experiment_id", experimentId).findList()));
    }


    /**
     * Create experiment.
     * @return
     */
    public Result createExperimentInstance() {
        URLShortenerService urlShortenerService = new URLShortenerService();

        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest(Utility.createResponse("Expecting json", false));
        }

        Form<ExperimentInstanceForm> experimentForm = formFactory.form(ExperimentInstanceForm.class).bindFromRequest();
        ExperimentInstance experimentInstance = new ExperimentInstance();
        experimentInstance.experimentInstanceName = experimentForm.get().experimentInstanceName;
        experimentInstance.actualURL = experimentForm.get().actualURL;
        //TODO: Revisit URL Shortner to see if we need a database call to check if URL exist
        //TODO: instead of hashmap. At the moment if Experiment Controller gets call at multiple times
        //TODO: we will be looking at empty hashmap.
        //TODO: ShortURL should be created server-side, not passed in from the client.
        experimentInstance.shortenURL = "TODO";
        experimentInstance.createdTime = new Timestamp(System.currentTimeMillis());
        experimentInstance.numberOfParticipants = experimentForm.get().numberOfParticipants;
        experimentInstance.priority = experimentForm.get().priority;
        experimentInstance.status = "ACTIVE";
        //experimentForm.get(); can we use this instead of using setters?
        experimentInstance.save();
        JsonNode jsonObject = Json.toJson(experimentInstance);
        return created(Utility.createResponse(jsonObject, true));
    }

    /**
     * Update experiment
     * @param id experiment id
     * @return
     */
    public Result updateExperimentInstance(Long id) {

        ExperimentInstance experimentInstance;
        Form<ExperimentInstance> experimentInstanceForm;

        try {
            experimentInstance = ExperimentInstance.find.byId(id);
            experimentInstanceForm = formFactory.form(ExperimentInstance.class).fill(experimentInstance);
            experimentInstance = experimentInstanceForm.get();
            experimentInstance.save();
            JsonNode jsonObject = Json.toJson(experimentInstance);
            return ok(Utility.createResponse(jsonObject, true));
        } catch (Exception ex) {
            return notFound("Something went wrong during update");
        }
    }


    /**
     * Delete experiment
     * @return
     */
    public Result deleteExperimentInstance(Long id) {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest(Utility.createResponse("Expecting json", false));
        } else {
            boolean status = ExperimentInstance.find.ref(id).delete();
            if (!status) {
                return notFound(Utility.createResponse(
                        "Experiment Instance with id:" + id + " not found", false));
            } else {
                return ok(Utility.createResponse(
                        "Experiment Instance with id:" + id + " deleted", true));
            }
        }
    }

    /**
     * Static class to hold experiment form values.
     */
    public static class ExperimentInstanceForm {

        @Constraints.Required
        @Constraints.MaxLength(255)
        public String experimentInstanceName;

        @Constraints.MaxLength(255)
        @Constraints.Required
        public String actualURL;

        @Constraints.Required
        public int numberOfParticipants;

        @Constraints.Required
        public int priority;

    }


}