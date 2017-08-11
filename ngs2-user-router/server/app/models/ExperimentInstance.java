package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.ebean.Finder;
import io.ebean.Model;
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
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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

    public static Finder<Long, ExperimentInstance> find = new Finder<Long, ExperimentInstance>(ExperimentInstance.class);

    /**
     * Get list of experiments by the priority.
     * @param priority
     * @return
     */
    public static List<ExperimentInstance> findExperimentInstancesByPriority(int priority) {
        return find.query().where()
                .eq("priority", priority).findList();
    }

    /**
     * Get list of experiments by status.
     * @param status
     * @return
     */
    public static List<ExperimentInstance> findExperimentInstancesByStatus(String status) {
        return find.query().where()
                .eq("status", status.toUpperCase()).findList();
    }

    /**
     * Get list of experiments by status and priority.
     * @param status status of the experiment
     * @param priority priority number of the experiment.
     * @return
     */
    public static List<ExperimentInstance> findExperimentInstancesByStatusAndPriority(String status, int priority) {
        return find.query().where()
                .eq("status", status.toUpperCase())
                .eq("priority", priority).findList();
    }

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

    // Assign a user to this experiment instance
    public void assignUserInfo(UserInfo user) {
        user.setCurrentGameUrl(getUserURL(user));
        user.setStatus("PLAYING");
        user.save();

        UserInfoExperimentInstance uiei = new UserInfoExperimentInstance();
        uiei.setExperimentInstance(this);
        uiei.setUserInfo(user);
        uiei.setArrivalTime(Timestamp.from(Instant.now()));
        uiei.save();
    }
}
