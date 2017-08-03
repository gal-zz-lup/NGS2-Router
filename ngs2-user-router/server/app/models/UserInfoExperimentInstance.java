package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.JsonIgnore;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;


import java.sql.Timestamp;
import java.util.List;

/**
 * Created by anuradha_uduwage.
 */
@Entity
public class UserInfoExperimentInstance extends Model {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long userExperimentId;

    @OneToOne
    @JsonIgnore
    private UserInfo userInfo;

    @OneToOne
    @JsonIgnore
    private Experiment experiment;

    @Constraints.Required
    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    public Timestamp arrivalTime;

    @Constraints.Required
    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    public Timestamp sendOffTime;

    public static Finder<Long, UserInfoExperimentInstance>
            find = new Finder<Long, UserInfoExperimentInstance>(UserInfoExperimentInstance.class);

    public static List<Experiment> findByExperiment(Experiment experiment) {
        return Experiment.find.where().eq("experiment", experiment).findList();
    }

    public static List<UserInfo> findByUser(UserInfo userInfo) {
        return UserInfo.find.where().eq("userInfo", userInfo).findList();
    }


    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Experiment getExperiment() {
        return experiment;
    }

    public void setExperiment(Experiment experiment) {
        this.experiment = experiment;
    }
}