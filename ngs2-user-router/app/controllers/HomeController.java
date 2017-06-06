package controllers;

import com.google.inject.Inject;
import play.db.Database;
import play.mvc.*;
import views.html.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    private Database db;

    @Inject
    public HomeController(Database db) {
        this.db = db;
    }

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        //TODO: parse elements from request
        //TODO: bind results to a model
        //TODO: convert model to json
        String experimentId = request().getQueryString("experiment");
        return ok(index.render("Router"));
    }

}
