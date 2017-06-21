package models;

import com.avaje.ebean.Model;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;


import java.sql.Timestamp;

/**
 * Created by anuradha_uduwage.
 */
@Entity
public class ExperimentUserInfo extends Model {

    @Id
    public Long userExperimentId;

    @ManyToOne
    public UserInfo userId;

    @ManyToOne
    public Experiment experimentId;

    @Constraints.Required
    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    public Timestamp arrivalTime;

    @Constraints.Required
    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    public Timestamp sendOffTime;

}