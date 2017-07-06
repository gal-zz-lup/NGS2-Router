package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;


/**
 * Created by anuradha_uduwage.
 */
@Entity
public class Admin extends Model {

    @Id
    public Long id;

    @Column(length = 255, unique = true, nullable = false)
    @Constraints.MaxLength(255)
    @Constraints.Required
    @Constraints.Email
    public String email;

    @Constraints.Required
    @Constraints.MinLength(6)
    @Constraints.MaxLength(256)
    private String password;

    private String authenticationToken;

    public void setEmail(String email) {

        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Column(length = 64, unique = true, nullable = false)
    private byte[] shaPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        shaPassword = getSha512(password);
    }

    /**
     * Create token to recognize authenticated user.
     * @return authenticationToken
     */
    public String createAuthToken() {
        authenticationToken = UUID.randomUUID().toString();
        save();
        return authenticationToken;
    }

    public void deleteAuthToken() {
        authenticationToken = null;
        save();
    }

    public Admin(String email, String password) {
        setEmail(email);
        setPassword(password);
    }


    public static Finder<Long, Admin> find = new Finder<Long, Admin>(Admin.class);

    /**
     * Find user by email address and hash value of the password.
     * @param email email address.
     * @param password raw passsword.
     * @return
     */
    public static Admin findByEmailAndPassword(String email, String password) {
        return find
                .where()
                .eq("email", email.toLowerCase())
                .eq("shaPassword", getSha512(password))
                .findUnique();
    }


    /**
     * Find user by email
     * @param email email address of the user.
     * @return
     */
    public static Admin findByEmail(String email) {
        return find
                .where()
                .eq("email", email.toLowerCase())
                .findUnique();
    }

    /**
     * Find user by authenticated token
     * @param authenticationToken authentication token.
     * @return
     */
    public static Admin findByAuthenticatedToken(String authenticationToken) {
        if (authenticationToken == null) {
            return null;
        } else {
            try {
                return find.where().eq("authenticationToken", authenticationToken).findUnique();
            } catch (Exception ex) {
                return null;
            }
        }
    }


    /**
     * Get SHA-512 hash of the password.
     * @param value raw password.
     * @return Hash value of the password
     */
    public static byte[] getSha512(String value) {
        try {
            return MessageDigest.getInstance("SHA-512").digest(value.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(); //TODO:need to add logging module and log these
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException();
        }
    }

}
