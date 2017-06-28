package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.ManyToOne;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by anuradha_uduwage on 6/27/17.
 */
public class Session extends Model {

    @ManyToOne
    @Constraints.Required
    private Admin adminUser;
    private Date loggedInDate;
    private Date lastSeenDate;

    public Session(Admin admin, Date loggedIn, Date lastSeen) {
        this.adminUser = admin;
        this.loggedInDate = loggedIn;
        this.lastSeenDate = lastSeen;
    }

    public Admin getAdminUser() {
        return adminUser;
    }

    public Date getLoggedInDate() {
        return loggedInDate;
    }

    public Date getLastSeenDate() {
        return lastSeenDate;
    }

}
