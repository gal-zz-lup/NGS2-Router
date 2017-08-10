package models;

import io.ebean.Finder;
import io.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.List;

/**
 * Created by anuradha_uduwage.
 */
@Entity
public class Experiment extends Model {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;

  @Column(length = 255, unique = true, nullable = false)
  @Constraints.MaxLength(255)
  @Constraints.Required
  public String experimentName;

  @OneToMany(mappedBy="experiment", cascade=CascadeType.ALL)
  public List<ExperimentInstance> experimentInstances;

  public static Finder<Long, Experiment> find = new Finder<Long, Experiment>(Experiment.class);

  public String getExperimentName() {
    return experimentName;
  }

  public void setExperimentName(String experimentName) {
    this.experimentName = experimentName;
  }
}
