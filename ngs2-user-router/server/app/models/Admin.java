package models;

import com.avaje.ebean.Finder;
import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;


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
    public String password;

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }



}
