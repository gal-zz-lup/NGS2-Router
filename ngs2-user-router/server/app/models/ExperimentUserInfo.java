package models;

import com.avaje.ebean.Model;
import javax.persistence.*;


import java.sql.Timestamp;

/**
 * Created by anuradha_uduwage.
 */
@Entity
public class ExperimentUserInfo extends Model {

    private final int userExperimentId;
    private final int userId;
    private final int experimentId;
    private final Timestamp arrivalTime;
    private final Timestamp sendOffTime;

    public ExperimentUserInfo(int userExperimentId, int userId, int experimentId,
                              Timestamp arrivalTime, Timestamp sendOffTime) {
        this.userExperimentId = userExperimentId;
        this.userId = userId;
        this.experimentId = experimentId;
        this.arrivalTime = arrivalTime;
        this.sendOffTime = sendOffTime;
    }


    public int getUserExperimentId() {
        return userExperimentId;
    }

    public int getUserId() {
        return userId;
    }

    public int getExperimentId() {
        return experimentId;
    }

    public Timestamp getArrivalTime() {
        return arrivalTime;
    }

    public Timestamp getSendOffTime() {
        return sendOffTime;
    }

}
