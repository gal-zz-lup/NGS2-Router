package models;

import play.data.validation.Constraints;

import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Created by anuradha_uduwage.
 */
public class Session {

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
