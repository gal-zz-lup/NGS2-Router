package models;

import java.sql.Timestamp;

/**
 * Created by anuradha_uduwage.
 */
public class UserInfo {

    private final int userId;
    private final int gallupId;
    private final Long randomizedId;
    private final Timestamp arrivalTime;

    /**
     *
     * @param userId
     * @param gallupId
     * @param randomizedId
     * @param arrivalTime
     */
    public UserInfo(int userId, int gallupId, Long randomizedId, Timestamp arrivalTime) {
        this.userId = userId;
        this.gallupId = gallupId;
        this.randomizedId = randomizedId;
        this.arrivalTime = arrivalTime;
    }

    /**
     *
     * @return
     */
    public int getUserId() {
        return userId;
    }

    /**
     *
     * @return
     */
    public int getGallupId() {
        return gallupId;
    }

    /**
     *
     * @return
     */
    public Long getRandomizedId() {
        return randomizedId;
    }

    /**
     *
     * @return
     */
    public Timestamp getArrivalTime() {
        return arrivalTime;
    }

}
