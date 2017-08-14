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
  @ManyToMany
  @JoinTable(name = "user_info_experiment_instance",
      joinColumns = @JoinColumn(name = "experiment_instance_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "user_info_id", referencedColumnName = "id"))
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

  public int getnParticipants() {
    return nParticipants;
  }

  public void setnParticipants(int nParticipants) {
    this.nParticipants = nParticipants;
  }

  public List<UserInfo> getUserInfoList() {
    return userInfoList;
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
   * Method to stop experiment instance.
   */
  public void stopExperimentInstance() {
    // Call this when stopping the experiment instance to reset the src and status of the players
    for (UserInfo user : userInfoList) {
      user.setStatus("FINISHED");
      user.setCurrentGameUrl("");
      user.update();
    }
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
