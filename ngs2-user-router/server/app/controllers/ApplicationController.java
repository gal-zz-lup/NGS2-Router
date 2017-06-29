package controllers;

import models.Admin;
import play.data.Form;
import play.data.FormFactory;
import play.data.validation.Constraints;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;

import javax.inject.Inject;
import java.io.File;


/**
 * Created by anuradha_uduwage
 */
public class ApplicationController extends Controller {

    /**
     * An action that renders login credentials to identify successful
     * login action.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    @Inject
    FormFactory formFactory;

    public Result login() {
        final Form<Login> loginForm = formFactory.form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(loginForm.errorsAsJson());
        }
        Login loggingInUser = loginForm.get();
        //Admin adminUser = Admin.

        return ok("Success!!!");
    }

    public Result uploadCSVFile() {
        try {
            MultipartFormData<File> body = request().body().asMultipartFormData();
            FilePart<File> csvFile = body.getFile("csvfile");

            if (csvFile != null) {
                String filename = csvFile.getFilename();
                String contentType = csvFile.getContentType();
                File file = csvFile.getFile();
                //need to parse the file and convert to json

            }
            return ok("File upload success!!!");
        } catch (Exception ex) {
            return internalServerError();
        }
    }


    /**x
     * Static class for the user credential.
     */
    public static class UserForm {
        @Constraints.Required
        @Constraints.Email
        public String email;
    }

    public static class Login extends UserForm {
        @Constraints.Required
        public String password;
    }


}
