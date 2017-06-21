package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;
import javax.validation.Constraint;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by anuradha_uduwage on 6/7/17.
 */
@Entity
public class Admin {

    @Id
    public Long id;

    @Column(length = 255, unique = true, nullable = false)
    @Constraints.MaxLength(255)
    @Constraints.Required
    @Constraints.Email
    public String username;

    @Column(length = 64, nullable = false)
    private byte[] shaPassword;

    /**
     * Set email address as the username.
     * @param username email address as the username
     */
    public void setUsername(String username) {
        this.username = username.toLowerCase();
    }

    /**
     * Take user password and set it as SHA-512 has value of the password.
     * @param password
     */
    public void setPassword(String password) {
        this.shaPassword = getShar512(password);
    }


    public static byte[] getShar512(String value) {
        try {
            return MessageDigest.getInstance("SHA-512").digest(value.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }




}
