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
public class ExperimentInstance extends Model {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long id;

    @OneToOne
    public Experiment experiment;

    public Long experimentId;

    @Column(length = 255, unique = true, nullable = false)
    @Constraints.MaxLength(255)
    @Constraints.Required
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
    public static List<ExperimentInstance> findExperimentInstancesByPriority(int priority) {
        return find.where()
                .eq("priority", priority).findList();
    }

    /**
     * Get list of experiments by status.
     * @param status
     * @return
     */
    public static List<ExperimentInstance> findExperimentInstancesByStatus(String status) {
        return find.where()
                .eq("status", status.toUpperCase()).findList();
    }

    /**
     * Get list of experiments by status and priority.
     * @param status status of the experiment
     * @param priority priority number of the experiment.
     * @return
     */
    public static List<ExperimentInstance> findExperimentInstancesByStatusAndPriority(String status, int priority) {
        return find.where()
                .eq("status", status.toUpperCase())
                .eq("priority", priority).findList();
    }
}
