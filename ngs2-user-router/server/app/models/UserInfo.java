package models;

import com.avaje.ebean.Model;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Created by anuradha_uduwage.
 */
@Entity
public class UserInfo extends Model {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Constraints.Required
    public Long userId;

    @Constraints.Required
    public String gallupId;

    @Column(name = "language", nullable = false)
    @Constraints.MaxLength(16)
    public String language;

    @Column(name = "randomized_id", columnDefinition = "TEXT")
    public Long randomizedId;

    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    public Timestamp arrivalTime;

    @Column(name = "status", length = 255, nullable = false)
    @Constraints.Required
    public String status;


    public static Finder<Long, UserInfo> find = new Finder<Long, UserInfo>(UserInfo.class);

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

    public Long getRandomizedId() {
        return randomizedId;
    }

    public void setRandomizedId(Long randomizedId) {
        this.randomizedId = randomizedId;
    }

    public Timestamp getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Timestamp arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}