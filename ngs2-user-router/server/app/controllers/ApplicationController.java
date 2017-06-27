package controllers;

import play.mvc.Controller;
import play.mvc.Result;


/**
 * Created by anuradha_uduwage
 */
public class ApplicationController extends Controller {

    public static Result login() {
        return ok("Success!!!");
    }
}
