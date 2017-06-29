package controllers;

import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import services.UserSessionWrapper;
import views.html.login;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Created by anuradha_uduwage.
 */
public class AuthenticationController extends Controller{

    @Inject
    FormFactory formFactory;

    private final UserSessionWrapper userSessionWrapper;

    public AuthenticationController() {
        userSessionWrapper = UserSessionWrapper.instance;
    }

    public Result login() {
        return ok(login.render(formFactory.form(LoginForm.class)));
    }

    public Result logout() {
        userSessionWrapper.logout(session("user"));
        session().clear();
        return redirect(routes.AuthenticationController.login());
    }
    public Result authenticateAdmin() {
        final Form<LoginForm> loginForm = formFactory.form(LoginForm.class).bindFromRequest();
        return Optional.ofNullable(loginForm.get())
                .map(form -> {
                    if (userSessionWrapper.login(form.email, form.password)) {
                        session().put("user", form.email);
                        return redirect(routes.ApplicationController.index());
                    } else {
                        // do we need to redirect the user to login or
                        // bit confuse since regular traffic doesn't need to go here.
                    }
                }).orElse(redirect(routes.AuthenticationController.login()));
        return ok("TODO: need to implement");
    }


}
