package controllers;

import models.UserInfo;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import play.mvc.Security;
import util.Utility;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;


/**
 * Created by anuradha_uduwage
 */
@Security.Authenticated(SecurityController.class)
public class FileUploadController extends Controller {

  public Result uploadCSVFile() {

    ArrayList<UserInfo> importedUsers = new ArrayList<>();
    //Logger.info("Getting ready to upload the csv file");
    try {
      MultipartFormData body = request().body().asMultipartFormData();
      FilePart file = body.getFile("file");
      File f = (File)file.getFile();
      Reader in = new FileReader(f);
      Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);

      for (CSVRecord record : records) {
        if (record.isSet("id") && record.isSet("language") && record.isSet("sample_group")) {
          String id = record.get("id");
          String language = record.get("language");
          String sampleGroup = record.get("sample_group");
          Logger.debug(id + "," + language + "," + sampleGroup);
          importedUsers.add(UserInfo.importUser(id, language, sampleGroup));
        } else {
          return internalServerError("CSV does not have required header row (id, language, sample_group).");
        }
      }
    } catch (Exception ex) {
      Logger.debug("exception thrown: " + ex.getMessage());
      return internalServerError();
    }
    return ok(Utility.createResponse(Json.toJson(importedUsers), true));
  }


}
