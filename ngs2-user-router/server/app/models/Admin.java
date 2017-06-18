package models;

import com.avaje.ebean.Model;
import javax.persistence.*;

/**
 * Created by anuradha_uduwage on 6/7/17.
 */
@Entity
public class Admin {

    @Id
    public Long id;

    @Constrains.Required
    public String username;
    public String password;


}
