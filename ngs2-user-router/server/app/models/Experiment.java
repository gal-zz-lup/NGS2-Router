package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.List;

/**
 * Created by anuradha_uduwage.
 */
@Entity
public class Experiment extends Model {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long id;

    @Column(length = 255, unique = true, nullable = false)
    @Constraints.MaxLength(255)
    @Constraints.Required
    public String experimentName;

    @OneToMany
    public List<ExperimentInstance> experimentInstances;

    public static Finder<Long, Experiment> find = new Finder<Long, Experiment>(Experiment.class);
}
