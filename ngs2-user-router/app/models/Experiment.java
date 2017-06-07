package models;

/**
 * Created by anuradha_uduwage.
 */
public class Experiment {

    private final int id;
    private final String actualURL;
    private final String shortenURL;
    private final int numberOfParticipants;
    private final String status;

    /**
     *
     * @param id
     * @param actualURL
     * @param shortenURL
     * @param numberOfParticipants
     * @param status
     */
    public Experiment(int id, String actualURL, String shortenURL, int numberOfParticipants, String status) {
        this.id = id;
        this.actualURL = actualURL;
        this.shortenURL = shortenURL;
        this.numberOfParticipants = numberOfParticipants;
        this.status = status;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return
     */
    public String getActualURL() {
        return actualURL;
    }

    /**
     *
     * @return
     */
    public String getShortenURL() {
        return shortenURL;
    }

    /**
     *
     * @return
     */
    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    /**
     *
     * @return
     */
    public String getStatus() {
        return status;
    }



}
