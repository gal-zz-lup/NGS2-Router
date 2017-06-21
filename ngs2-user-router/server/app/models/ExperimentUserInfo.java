package models;

import com.avaje.ebean.Model;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;


import java.sql.Timestamp;

/**
 * Created by anuradha_uduwage.
 */
@Entity
public class ExperimentUserInfo extends Model {

    @Id
    public Long userExperimentId;

    @ManyToOne
    public UserInfo userId;

    @ManyToOne
    public Experiment experimentId;

    @Constraints.Required
    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    public Timestamp arrivalTime;

    @Constraints.Required
    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    public Timestamp sendOffTime;


}
/*
CREATE TABLE experiment_user_info (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  user_info_id bigint(20) NOT NULL,
  FOREIGN KEY (user_info_id) REFERENCES user_info(id),
  experiment_id bigint(20) NOT NULL,
  FOREIGN KEY (experiment_id) REFERENCES experiment(id),
  arrival_time TIMESTAMP NOT NULL,
  send_off_time TIMESTAMP NOT NULL,
  PRIMARY KEY (id)
);
 */