package services;

import com.google.common.base.Preconditions;
import models.Admin;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.*;

/**
 * Wrapper class for SessionManager and place holder for users.
 *
 * Created by anuradha_uduwage.
 */
public class UserSessionWrapper {

    private final Map<String, Admin> userRepo;
    private final SessionManager sessionManager;
    private final String salt;

    public static final UserSessionWrapper instance = new UserSessionWrapper();

    /**
     * Constructor of the class create a HashMap, salt.
     */
    private UserSessionWrapper() {
        this.userRepo = new HashMap<>();
        this.salt = BCrypt.gensalt();
        this.sessionManager = SessionManager.instance;
    }

    /**
     * Method takes password and generate hash version with salting.
     * @param value password to be hashed
     * @return Returns hashed, salted password
     */
    private String bCryptHash(String value) {
        return BCrypt.hashpw(value, salt);
    }

    /**
     * Method receives email, password and checks for duplicate users if admin email address
     * doesn't exist creates the user and store it hash map.
     * @param email
     * @param password
     * @return Return newly created object type <Code>Admin</Code>
     */
    public Admin create(String email, String password) {
        final Optional<Admin> potentialUser = Optional.ofNullable(userRepo.get(email));
        Preconditions.checkArgument(!potentialUser.isPresent(), "User already exist in the system");

        Admin admin = new Admin();
        admin.setEmail(email);
        admin.setPassword(bCryptHash(password));
        admin.save();
        userRepo.put(email, admin);
        return admin;
    }

    /**
     * Method receives email, password and check if exits, and if exits compare the passwords.
     * If the password match session get creates using <code>SessionManager</code>
     * @param email email address of the admin user
     * @param password password of the user.
     * @return Returns Boolean True if password matches False otherwise.
     */
    public boolean login(String email, String password) {
        return Optional.ofNullable(userRepo.get(email)).map(
                user -> {
                    boolean validPassword = compare(password, user.getPassword());
                    if (validPassword) {
                        sessionManager.login(user);
                    }
                    return validPassword;
                }).orElse(false);
    }

    /**
     * Method compare the password with its hashed version.
     * @param password password
     * @param hashedPassword hashed version of the password
     * @return
     */
    public boolean compare(String password, String hashedPassword) {
        return bCryptHash(password).equals(hashedPassword);
    }

    /**
     * Wrapper method for logout method in <code>SessionManager</code>
     * @param email
     */
    public void logout(String email) {
        this.sessionManager.logout(email);
    }

    /**
     * Wrapper method for logout method in <code>SessionManager</code>
     * @param email
     * @return
     */
    public boolean isLoggedIn(String email) {
        return this.sessionManager.isLoggedIn(email);
    }



}
