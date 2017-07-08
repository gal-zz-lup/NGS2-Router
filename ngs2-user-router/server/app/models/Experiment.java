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
    public String experimentName;

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

    public static Finder<Long, Experiment> find = new Finder<Long, Experiment>(Experiment.class);

    public long getId() {
        return id;
    }

    public String getExperimentName() {
        return experimentName;
    }

    public String getActualURL() {
        return actualURL;
    }

    public String getShortenURL() {
        return shortenURL;
    }

    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public String getStatus() {
        return status;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setExperimentName(String experimentName) {
        this.experimentName = experimentName;
    }

    public void setActualURL(String actualURL) {
        this.actualURL = actualURL;
    }

    public void setShortenURL(String shortenURL) {
        this.shortenURL = shortenURL;
    }

    public void setNumberOfParticipants(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }
}
