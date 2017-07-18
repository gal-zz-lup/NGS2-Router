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
    public List<UserInfo> users;

    @ManyToOne
    public List<Experiment> experiments;

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

    /**
     * Return list of users
     * @return
     */
    public List<UserInfo> getUsers() {

        return users;
    }

    public void setUserId(List<UserInfo> users) {

        this.users = users;
    }

    public List<Experiment> getExperiments() {

        return experiments;
    }

    public void setExperiment(List<Experiment> experiment) {

        this.experiments = experiments;
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