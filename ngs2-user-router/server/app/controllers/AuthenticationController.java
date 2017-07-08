package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Admin;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import javax.inject.Inject;

/**
 * Created by anuradha_uduwage.
 */
public class AuthenticationController extends Controller{

    @Inject
    FormFactory formFactory;

    public final static String AUTH_TOKEN_HEADER = "X-AUTH-TOKEN";
    public final static String AUTH_TOKEN = "authenticationToken";

    public static Admin getAdmin() {

        return (Admin) Http.Context.current().args.get("adminUser");
    }


    /**
     * Method establish authentication token and returns the authentication token.
     * @return
     */
    public Result login() {

        Form<LoginForm> loginForm = formFactory.form(LoginForm.class).bindFromRequest();

        if (loginForm.hasErrors()) {
            return badRequest(loginForm.errorsAsJson());
        }

        LoginForm login = loginForm.get();

        Admin adminUser = Admin.findByEmailAndPassword(login.email, login.password);

        if (adminUser == null) {
            return unauthorized();
        } else {
            String authenticationToken = adminUser.createAuthToken();
            ObjectNode authTokenJson = Json.newObject();
            authTokenJson.put(AUTH_TOKEN, authenticationToken);
            response().setCookie(Http.Cookie.builder(AUTH_TOKEN, authenticationToken).withSecure(ctx()
                    .request().secure()).build());
            return ok(authTokenJson);
        }
    }

    public Result isAuthenticated() {
        if (getAdmin() != null ) {
           return ok("User is logged in");
        }
        else return unauthorized();
    }

    /**
     * Logout the user and redirect to the root.
     * @return
     */
    @Security.Authenticated(SecurityController.class)
    public Result logout() {
        response().discardCookie(AUTH_TOKEN);
        getAdmin().deleteAuthToken();
        return redirect("/");
    }

}
