package models;

import io.ebean.Finder;
import io.ebean.Model;
import org.apache.commons.lang3.RandomStringUtils;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by anuradha_uduwage.
 */
@Entity
public class UserInfo extends Model {

  public static Finder<Long, UserInfo> find = new Finder<Long, UserInfo>(UserInfo.class);
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  @Constraints.Required
  public Long userId;
  @Constraints.Required
  public String gallupId;
  @Column(name = "language", nullable = false)
  @Constraints.MaxLength(16)
  public String language;
  @Column(name = "randomized_id", length = 255)
  public String randomizedId;
  @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
  public Timestamp arrivalTime;
  @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
  public Timestamp lastCheckIn;
  @Column(name = "status", length = 255, nullable = false)
  @Constraints.Required
  public String status;
  @Column(name = "current_game_url", length = 255)
  public String currentGameUrl;
  @Column(name = "sample_group", length = 255, nullable = false)
  @Constraints.Required
  public String sampleGroup;

  @ManyToMany(mappedBy = "userInfoList")
  public List<ExperimentInstance> experimentInstanceList;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getGallupId() {
    return gallupId;
  }

  public void setGallupId(String gallupId) {
    this.gallupId = gallupId;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String getRandomizedId() {
    return randomizedId;
  }

  public void setRandomizedId(String randomizedId) {
    this.randomizedId = randomizedId;
  }

  public Timestamp getArrivalTime() {
    return arrivalTime;
  }

  public void setArrivalTime(Timestamp arrivalTime) {
    this.arrivalTime = arrivalTime;
  }

  public Timestamp getLastCheckIn() {
    return lastCheckIn;
  }

  public void setLastCheckIn(Timestamp lastCheckIn) {
    this.lastCheckIn = lastCheckIn;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getSampleGroup() {
    return sampleGroup;
  }

  public void setSampleGroup(String sampleGroup) {
    this.sampleGroup = sampleGroup;
  }

  public String getCurrentGameUrl() {
    return currentGameUrl;
  }

  public void setCurrentGameUrl(String currentGameUrl) {
    this.currentGameUrl = currentGameUrl;
  }


  /**
   * Set the properties of UserInfo object.
   * @param gallupId
   * @param language
   * @param sampleGroup
   * @return
   */
  public static UserInfo importUser(String gallupId, String language, String sampleGroup) {
    UserInfo user = new UserInfo();
    user.setGallupId(gallupId);
    user.setLanguage(language);
    user.setSampleGroup(sampleGroup);
    user.setStatus("NEW");
    user.setRandomizedId(createRandomId());
    user.save();
    return user;
  }

  private static String createRandomId() {
    String randomId = "";
    boolean unique = false;
    while (!unique) {
      randomId = RandomStringUtils.randomAlphanumeric(8);
      if (UserInfo.find.query().where().eq("randomized_id", randomId).findCount() == 0) {
        unique = true;
      }
    }
    return randomId;
  }

  public boolean hasParticipatedInExperiment(Experiment experiment) {
    for (ExperimentInstance ei : experimentInstanceList) {
      if (ei.experiment.equals(experiment)) {
        return true;
      }
    }
    return false;
  }

}