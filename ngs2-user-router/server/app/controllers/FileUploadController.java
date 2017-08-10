package controllers;

import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.data.validation.Constraints;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import play.mvc.Security;
import util.CSVReader;
import util.Utility;

import javax.inject.Inject;
import java.io.File;


/**
 * Created by anuradha_uduwage
 */
@Security.Authenticated(SecurityController.class)
public class FileUploadController extends Controller {

  /**
   * An action that renders login credentials to identify successful
   * login action.
   * The configuration in the <code>routes</code> file means that
   * this method will be called when the application receives a
   * <code>GET</code> request with a path of <code>/</code>.
   */
  @Inject
  FormFactory formFactory;

  public Result index() {
    return ok("Hello");
  }

  @BodyParser.Of(BodyParser.Raw.class)
  public Result uploadCSVFile() {

    File file = request().body().asRaw().asFile();
    Logger.info(file.getName());
    Logger.info("Getting ready to upload the csv file");
    try {
      MultipartFormData<File> body = request().body().asMultipartFormData();
      FilePart<File> csvFile = body.getFile("csvfile");
      CSVReader csvReader;
      if (csvFile != null && csvFile.getFilename().contains(".csv")) {
        try {
          csvReader = new CSVReader(csvFile.getFilename());
          csvReader.parseFile();

        } catch (Exception ex) {
          Logger.error("Exception occured::: " + ex.getMessage());
          return status(400, "Missing file");
        }
      }
    } catch (Exception ex) {
      return internalServerError();
    }
    return ok(Utility.createResponse("File was successfully loaded and parse", true));
  }

  /**
   * Static class for the form.
   */
  public static class FileUploadForm {

    @Constraints.Required
    public String filePath;
  }

}
