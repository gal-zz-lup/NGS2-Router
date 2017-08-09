package controllers;

import play.data.validation.Constraints;

/**
 * Created by anuradha_uduwage.
 */
public class LoginForm {

  @Constraints.Required
  @Constraints.Email
  public String email;

  @Constraints.Required
  public String password;
}
