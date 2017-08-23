package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.ebean.Finder;
import io.ebean.Model;
import play.Logger;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anuradha_uduwage.
 */
@Entity
public class ExperimentInstance extends Model {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;

  @ManyToOne
  @JsonIgnore
  public Experiment experiment;

  @Column(length = 255, unique = true, nullable = false)
  @Constraints.MaxLength(255)
  @Constraints.Required
  public String experimentInstanceName;

  @Column(name = "experiment_instance_url_actual", length = 255, unique = true, nullable = false)
  @Constraints.MaxLength(255)
  @Constraints.Required
  public String experimentInstanceUrlActual;

  @Column(name = "experiment_instance_url_short", length = 255, unique = true, nullable = false)
  @Constraints.MaxLength(255)
  @Constraints.Required
  public String experimentInstanceUrlShort;

  @Column(name = "n_participants")
  public int nParticipants;

  @Column(name = "priority")
  public int priority;

  @Column(columnDefinition = "TEXT")
  @Constraints.Required
  public String status;

  @Constraints.Required
  @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
  public Timestamp createdTime;

  @Constraints.Required
  @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
  public Timestamp updatedTime;
  
  @JsonIgnore
  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(name = "user_info_experiment_instance",
      joinColumns = {@JoinColumn(name = "experiment_instance_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "user_info_id", referencedColumnName = "id")})
  public List<UserInfo> userInfoList;

  public static Finder<Long, ExperimentInstance> find = new Finder<Long, ExperimentInstance>(ExperimentInstance.class);

  /**
   * Get list of experiments by the priority.
   *
   * @param priority
   * @return
   */
  public static List<ExperimentInstance> findExperimentInstancesByPriority(int priority) {
    return find.query().where()
        .eq("priority", priority).findList();
  }

  /**
   * Get list of experiments by status.
   *
   * @param status
   * @return
   */
  public static List<ExperimentInstance> findExperimentInstancesByStatus(String status) {
    return find.query().where()
        .eq("status", status.toUpperCase()).findList();
  }

  /**
   * Get list of experiments by status and priority.
   *
   * @param status   status of the experiment
   * @param priority priority number of the experiment.
   * @return
   */
  public static List<ExperimentInstance> findExperimentInstancesByStatusAndPriority(String status, int priority) {
    return find.query().where()
        .eq("status", status.toUpperCase())
        .eq("priority", priority).findList();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getnParticipants() {
    return nParticipants;
  }

  public void setnParticipants(int nParticipants) {
    this.nParticipants = nParticipants;
  }

  public String getExperimentInstanceName() {
    return experimentInstanceName;
  }

  public void setExperimentInstanceName(String experimentInstanceName) {
    this.experimentInstanceName = experimentInstanceName;
  }

  public String getExperimentInstanceUrlActual() {
    return experimentInstanceUrlActual;
  }

  public void setExperimentInstanceUrlActual(String experimentInstanceUrlActual) {
    this.experimentInstanceUrlActual = experimentInstanceUrlActual;
  }

  public String getExperimentInstanceUrlShort() {
    return experimentInstanceUrlShort;
  }

  public void setExperimentInstanceUrlShort(String experimentInstanceUrlShort) {
    this.experimentInstanceUrlShort = experimentInstanceUrlShort;
  }

  public int getPriority() {
    return priority;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Timestamp getCreatedTime() {
    return createdTime;
  }

  public void setCreatedTime(Timestamp createdTime) {
    this.createdTime = createdTime;
  }

  public Timestamp getUpdatedTime() {
    return updatedTime;
  }

  public void setUpdatedTime(Timestamp updatedTime) {
    this.updatedTime = updatedTime;
  }

  public Experiment getExperiment() {
    return experiment;
  }

  public void setExperiment(Experiment experiment) {
    this.experiment = experiment;
  }

  public List<UserInfo> getUserInfoList() {
    return userInfoList;
  }

  public void setUserInfoList(List<UserInfo> userInfoList) {
    this.userInfoList = userInfoList;
  }

  /**
   * Generate Unique URL for user.
   * @param user
   * @return
   */
  private String getUserURL(UserInfo user) {
    String url = this.experimentInstanceUrlActual;

    if (user.getLanguage() != null) {
      url = url.replace("{language}", user.getLanguage());
    }

    if (user.getRandomizedId() != null) {
      url = url.replace("{id}", user.getRandomizedId());
    }

    return url;
  }

  /**
   * Method to stop experiment instance and set the status of the users who were playing in the instance
   * to be completed state.
   * @param experimentInstance object contains experiment instance info and user info of that instance.
   */
  public void stopExperimentInstance(ExperimentInstance experimentInstance) {
    // Getting all the records relevant to experiment instance from user_info_experiment_instance table
    List<UserInfoExperimentInstance> uieiList = UserInfoExperimentInstance.getUsersInfoExperimentByInstanceId(
            experimentInstance.getId());

    Logger.info("Stopping Experiment Instance " + experimentInstance.getId() + "::" +
            experimentInstance.getExperimentInstanceName());

    for (UserInfoExperimentInstance uiei : uieiList) {
      // Iterating over the users who are attached to the experiment instance to change status.
      for (UserInfo user : uiei.getExperimentInstance().getUserInfoList()) {
        user.setStatus("FINISHED");
        user.setCurrentGameUrl("");
        user.update();
      }
      uiei.setSendOffTime(uiei.getSendOffTime());
      uiei.setArrivalTime(uiei.getArrivalTime());
      uiei.update();
    }
    // Updating the experiment instance table to reflect stoppage.
    experimentInstance.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
    experimentInstance.update();
  }


  /**
   * Populate user information and change state in UserInfo,
   * UserInforExperimentInstance and ExperimentInstance.
   *
   * @param users
   */
  public void assignUserInfo(List<UserInfo> users) {
    Logger.debug("user.size() = " + users.size());
    for (UserInfo user : users) {
      user.setCurrentGameUrl(getUserURL(user));
      user.setStatus("PLAYING");
      user.save();

      UserInfoExperimentInstance uiei = new UserInfoExperimentInstance();
      uiei.setExperimentInstance(this);
      uiei.setUserInfo(user);
      uiei.setArrivalTime(user.getArrivalTime());
      uiei.setSendOffTime(Timestamp.from(Instant.now()));
      uiei.save();

      this.status = "PLAYING";
      this.save();
    }
  }
}
