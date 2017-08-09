package controllers;

import models.Admin;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Created by anuradha_uduwage.
 */
public class SecurityController extends Security.Authenticator {


  @Override
  public String getUsername(Http.Context context) {
    String[] authTokenHeaderValues = context.request().headers().get(AuthenticationController.AUTH_TOKEN_HEADER);
    if ((authTokenHeaderValues != null) &&
        (authTokenHeaderValues.length == 1) && authTokenHeaderValues[0] != null) {
      Admin adminUser = models.Admin.findByAuthenticatedToken(authTokenHeaderValues[0]);
      if (adminUser != null) {
        context.args.put("adminUser", adminUser);
        return adminUser.getEmail();
      }
    }
    return null;
  }

  @Override
  public Result onUnauthorized(Http.Context context) {
    // wonder if we should re-direct to index page.
    return unauthorized();
  }


}
