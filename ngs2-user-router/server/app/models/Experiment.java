package models;

import com.avaje.ebean.Model;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.Constraint;
import java.sql.Timestamp;

/**
 * Created by anuradha_uduwage.
 */
@Entity
public class Experiment extends Model {

    @Id
    public long id;

    @Column(length = 255, unique = true, nullable = false)
    @Constraints.MaxLength(255)
    @Constraints.Required
    public String actualURL;

    @Column(length = 255, unique = true, nullable = false)
    @Constraints.MaxLength(255)
    @Constraints.Required
    public String shortenURL;

    public int numberOfParticipants;

    @Column(columnDefinition = "TEXT")
    @Constraints.Required
    public String status;

    @Constraints.Required
    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    public Timestamp createdTime;



}
