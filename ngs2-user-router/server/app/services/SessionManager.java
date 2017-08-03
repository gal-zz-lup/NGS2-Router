package services;

import com.google.common.base.Preconditions;
import models.Admin;
import models.Session;
import play.Configuration;
import play.api.Play;


import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Class to maintain login sessions and handle login and logout.
 *
 * Created by anuradha_uduwage.
 */
@Deprecated
public class SessionManager {

    private final Map<String, Session> sessionRepo;
    private final long timeLength;

    @Inject
    private static Configuration configuration;

    public static final SessionManager instance = new SessionManager();

    /**
     * Constructor of the class create new HashMap to maintain sessions and read in the
     * pre-define max time for session inactivity.
     */
    private SessionManager() {
        this.sessionRepo = new HashMap<>();
        this.timeLength = configuration.getLong("admin.inactivity.max");
    }

    /**
     * Method receives admin email address and retrieves the session assigned to it in the hashmap.
     * Then method checks for session expiration. If session is not expired it returns boolean value true.
     * @param email admin email address
     * @return Returns boolean value True or False
     */
    public boolean isLoggedIn(String email) {
        final Session session = sessionRepo.get(email);
        final boolean loggedIn = Optional.ofNullable(session).map(s -> {
            Date now = new Date();
            final long inactiveTime = now.getTime() - s.getLastSeenDate().getTime();
            return inactiveTime < timeLength;
        }).orElse(false);

        if(!loggedIn) {
            sessionRepo.remove(email);
        } else {
            sessionRepo.put(email, new Session(session.getAdminUser(), session.getLoggedInDate(), new Date()));
        }
        return loggedIn;
    }

    /**
     * Method checks if admin user is already logged into the system. If user logged in created a new
     * session with current date time.
     * @param adminUser admin email address
     */
    public void login(Admin adminUser) {
        Preconditions.checkArgument(!isLoggedIn(adminUser.getEmail()), "User is already logged in");
        final Date now = new Date();
        sessionRepo.put(adminUser.getEmail(), new Session(adminUser, now, now));
    }

    /**
     * Method removes any sessions that are assigned to the user.
     * @param email admin email address
     */
    public void logout(String email) {
        sessionRepo.remove(email);
    }
}
