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
import play.mvc.Security;
import services.URLShortenerService;
import util.Utility;

import javax.inject.Inject;
import java.sql.Timestamp;


/**
 * Created by anuradha_uduwage.
 */
@Security.Authenticated(SecurityController.class)
public class ExperimentInstanceController extends Controller {

  @Inject
  FormFactory formFactory;

  /**
   * Get all experiment instances
   *
   * @return return list of experiment instances.
   */
  public Result getAllExperimentInstances() {

    return ok(Json.toJson(ExperimentInstance.find.all()));
  }

  /**
   * Get all experiment instances of a certain experiment type
   *
   * @return return list of experiment instances.
   */
  public Result getAllExperimentInstancesByExperiment(long experimentId) {
    return ok(Json.toJson(ExperimentInstance.find.query().where().eq("experiment_id", experimentId).findList()));
  }


  /**
   * Create experiment instance
   *
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

    experimentInstance.experiment = Experiment.find.byId(experimentForm.get().experimentId);
    experimentInstance.experimentInstanceName = experimentForm.get().experimentInstanceName;
    experimentInstance.experimentInstanceUrlActual = experimentForm.get().experimentInstanceUrlActual;
    //TODO: Revisit URL Shortner to see if we need a database call to check if URL exist
    //TODO: instead of hashmap. At the moment if Experiment Controller gets call at multiple times
    //TODO: we will be looking at empty hashmap.
    //TODO: ShortURL should be created server-side, not passed in from the client.
    experimentInstance.experimentInstanceUrlShort = urlShortenerService.getShortURL(
        experimentInstance.experimentInstanceUrlActual);
    experimentInstance.createdTime = new Timestamp(System.currentTimeMillis());
    experimentInstance.updatedTime = experimentInstance.createdTime;
    experimentInstance.nParticipants = experimentForm.get().nParticipants;
    experimentInstance.priority = experimentForm.get().priority;
    experimentInstance.status = "ACTIVE";
    //experimentForm.get(); can we use this instead of using setters?
    experimentInstance.save();
    JsonNode jsonObject = Json.toJson(experimentInstance);
    return created(Utility.createResponse(jsonObject, true));
  }

  /**
   * Update experiment instance
   *
   * @param id experiment instance id
   * @return
   */
  public Result updateExperimentInstance(Long id) {
    URLShortenerService urlShortenerService = new URLShortenerService();
    ExperimentInstance experimentInstance = ExperimentInstance.find.byId(id);
    Form<ExperimentInstanceForm> experimentForm = formFactory.form(ExperimentInstanceForm.class).bindFromRequest();
    experimentInstance.setExperimentInstanceName(experimentForm.get().experimentInstanceName);
    experimentInstance.setExperimentInstanceUrlActual(experimentForm.get().experimentInstanceUrlActual);
    experimentInstance.setExperimentInstanceUrlShort(urlShortenerService.getShortURL(
        experimentInstance.experimentInstanceUrlActual));
    experimentInstance.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
    experimentInstance.setnParticipants(experimentForm.get().nParticipants);
    experimentInstance.setPriority(experimentForm.get().priority);
    experimentInstance.setStatus(experimentForm.get().status);
    if (experimentInstance.getStatus().equals("STOPPED")) {
      experimentInstance.stopExperimentInstance(experimentInstance);
    }
    experimentInstance.update();
    JsonNode jsonObject = Json.toJson(experimentInstance);
    return ok(Utility.createResponse(jsonObject, true));
  }


  /**
   * Delete experiment instance
   *
   * @return
   */
  public Result deleteExperimentInstance(Long id) {
    boolean status = ExperimentInstance.find.ref(id).delete();
    if (!status) {
      return notFound(Utility.createResponse(
          "Experiment Instance with id:" + id + " not found", false));
    } else {
      return ok(Utility.createResponse(
          "Experiment Instance with id:" + id + " deleted", true));
    }
  }

  /**
   * Static class to hold experiment instance form values.
   */
  public static class ExperimentInstanceForm {
    public Long experimentId;

    @Constraints.Required
    @Constraints.MaxLength(255)
    public String experimentInstanceName;

    @Constraints.MaxLength(255)
    @Constraints.Required
    public String experimentInstanceUrlActual;

    @Constraints.Required
    public int nParticipants;

    @Constraints.Required
    public int priority;

    @Constraints.MaxLength(255)
    public String status;
  }

}