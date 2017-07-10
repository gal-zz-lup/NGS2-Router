package models;

import com.avaje.ebean.Model;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;


import java.sql.Timestamp;
import java.util.List;

/**
 * Created by anuradha_uduwage.
 */
@Entity
public class ExperimentUserInfo extends Model {

    @Id
    public Long userExperimentId;

    @ManyToOne
    public UserInfo user;

    @ManyToOne
    public Experiment experiment;

    @Constraints.Required
    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    public Timestamp arrivalTime;

    @Constraints.Required
    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    public Timestamp sendOffTime;

    public static Finder<Long, ExperimentUserInfo>
            find = new Finder<Long, ExperimentUserInfo>(ExperimentUserInfo.class);

    public static List<Experiment> findByExperiment(Experiment experiment) {
        return Experiment.find.where().eq("experiment", experiment).findList();
    }

    public static List<UserInfo> findByUser(UserInfo userInfo) {
        return UserInfo.find.where().eq("userInfo", userInfo).findList();
    }


    public Long getUserExperimentId() {
        return userExperimentId;
    }

    public void setUserExperimentId(Long userExperimentId) {
        this.userExperimentId = userExperimentId;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUserId(UserInfo user) {
        this.user = user;
    }

    public Experiment getExperiment() {
        return experiment;
    }

    public void setExperiment(Experiment experiment) {
        this.experiment = experiment;
    }

    public Timestamp getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Timestamp arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Timestamp getSendOffTime() {
        return sendOffTime;
    }

    public void setSendOffTime(Timestamp sendOffTime) {
        this.sendOffTime = sendOffTime;
    }
}