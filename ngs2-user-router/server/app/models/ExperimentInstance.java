package models;

import com.avaje.ebean.Model;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by anuradha_uduwage on 7/24/17.
 */
public class ExperimentInstance extends Model {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;

  @OneToMany
  private Experiment experiment;

  @Column(name = "experiment_instance_name", length = 255, unique = true, nullable = false)
  public String experimentInstanceName;

  @Column(name = "experiment_url_actual", length = 255, unique = true, nullable = false)
  @Constraints.MaxLength(255)
  @Constraints.Required
  public String actualURL;

  @Column(name = "experiment_url_short", length = 255, unique = true, nullable = false)
  @Constraints.MaxLength(255)
  @Constraints.Required
  public String shortenURL;

  @Column(name = "n_participants")
  public int numberOfParticipants;

  @Column(name = "priority")
  public int priority;

  @Column(columnDefinition = "TEXT")
  @Constraints.Required
  public String status;

  @Constraints.Required
  @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
  public Timestamp createdTime;

  public static Finder<Long, ExperimentInstance> find = new Finder<Long, ExperimentInstance>(ExperimentInstance.class);

  /**
   * Get list of experiments by the priority.
   * @param priority
   * @return
   */
  public static List<ExperimentInstance> findExperimentsByPriority(int priority) {
    return find.where()
            .eq("priority", priority).findList();
  }

  /**
   * Get list of experiments by status.
   * @param status
   * @return
   */
  public static List<ExperimentInstance> findExperimentsByStatus(String status) {
    return find.where()
            .eq("status", status.toUpperCase()).findList();
  }

  /**
   * Get list of experiments by status and priority.
   * @param status status of the experiment
   * @param priority priority number of the experiment.
   * @return
   */
  public static List<ExperimentInstance> findExperimentsByStatusAndPriority(String status, int priority) {
    return find.where()
            .eq("status", status.toUpperCase())
            .eq("priority", priority).findList();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Experiment getExperiment() {
    return experiment;
  }

  public void setExperiment(Experiment experiment) {
    this.experiment = experiment;
  }

  public String getExperimentInstanceName() {
    return experimentInstanceName;
  }

  public void setExperimentInstanceName(String experimentInstanceName) {
    this.experimentInstanceName = experimentInstanceName;
  }

  public String getActualURL() {
    return actualURL;
  }

  public void setActualURL(String actualURL) {
    this.actualURL = actualURL;
  }

  public String getShortenURL() {
    return shortenURL;
  }

  public void setShortenURL(String shortenURL) {
    this.shortenURL = shortenURL;
  }

  public int getNumberOfParticipants() {
    return numberOfParticipants;
  }

  public void setNumberOfParticipants(int numberOfParticipants) {
    this.numberOfParticipants = numberOfParticipants;
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
}
