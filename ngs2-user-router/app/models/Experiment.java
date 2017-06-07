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

    public Experiment(int id, String actualURL, String shortenURL, int numberOfParticipants, String status) {
        this.id = id;
        this.actualURL = actualURL;
        this.shortenURL = shortenURL;
        this.numberOfParticipants = numberOfParticipants;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getActualURL() {
        return actualURL;
    }

    public String getShortenURL() {
        return shortenURL;
    }

    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public String getStatus() {
        return status;
    }



}
