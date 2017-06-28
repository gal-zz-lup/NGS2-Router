package controllers;

import play.data.Form;
import play.data.FormFactory;
import play.data.validation.Constraints;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;


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
        //final Form<Login> loginForm = formFactory.form(Login.class).bindFromRequest();
        return ok("Success!!!");
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
