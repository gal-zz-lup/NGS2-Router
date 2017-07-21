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
    public Long gallupId;

    @Column(length = 255, unique = true, nullable = false)
    @Constraints.MaxLength(255)
    @Constraints.Required
    @Constraints.Email
    public String email;

    @Constraints.MaxLength(16)
    public String language;

    @Column(columnDefinition = "TEXT")
    public Long randomizedId;

    @Constraints.Required
    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    public Timestamp arrivalTime;

    public static Finder<Long, UserInfo> find = new Finder<Long, UserInfo>(UserInfo.class);

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGallupId() {
        return gallupId;
    }

    public void setGallupId(Long gallupId) {
        this.gallupId = gallupId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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


}