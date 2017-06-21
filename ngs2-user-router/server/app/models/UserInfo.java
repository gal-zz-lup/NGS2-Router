package models;

import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created by anuradha_uduwage.
 */
@Entity
public class UserInfo {

    @Id
    @Constraints.Required
    public Long userId;

    @Constraints.Required
    public Long gallupId;

    @Column(length = 255, unique = true, nullable = false)
    @Constraints.MaxLength(255)
    @Constraints.Required
    @Constraints.Email
    public String email;

    @Column(columnDefinition = "TEXT")
    public String language;

    @Constraints.Required
    public Long randomizedId;

    @Constraints.Required
    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    public Timestamp arrivalTime;

}