package services;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by anuradha_uduwage.
 */
public class URLShortenerService {

    private int urlLength;

    private HashMap<String, String> shortURLConvertToLong;
    private HashMap<String, String> longURLConvertToShort;

    private Random randomNumberGen;

    private String baseDomain;

    private char charList[];

    /**
     * Constructor of the class initialize all the private variable.
     */
    public URLShortenerService() {

        urlLength = 8;
        shortURLConvertToLong = new HashMap<>();
        longURLConvertToShort = new HashMap<>();
        randomNumberGen = new Random();
        baseDomain = "http://yale.server.address.edu";
        charList = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
    }

    /**
     * Returns the cleanup url
     * @param url url to be clean
     * @return
     */
    private String cleanupURL(String url) {

        if (url.substring(0,8).equals("https://") || url.substring(0,7).equals("http://")) {
            url = url.substring(0,7);
        }
        if (url.charAt(url.length() - 1) == '/') {
            url = url.substring(0, url.length() - 1);
        }

        return url;
    }

    /**
     * Method take the url and generate the short URL while keeping a record in hashmap.
     * @param url original url.
     * @return
     */
    private String buildShortURL(String url) {

        String shortURL = null;

        boolean urlExists = false;

        while(!urlExists) {
            shortURL = null;
            for (int i=0; i < urlLength; i++) {
                shortURL += charList[randomNumberGen.nextInt(62)];
            }

            if (shortURLConvertToLong.containsKey(shortURL)) {
                urlExists = true;
                break;
            }
        }

        shortURLConvertToLong.put(shortURL, url);
        longURLConvertToShort.put(url, shortURL);

        return shortURL;
    }

    /**
     * Generate short url if doesn't exist and return the short url.
     * If the shortURL exsist in the hashmap returns the existing shortURL.
     * @param longURL original long url to be shorten.
     * @return
     */
    public String getShortURL(String longURL) {

        String shortURL = null;

        longURL = cleanupURL(longURL);

        if (longURLConvertToShort.containsKey(longURL)) {
            shortURL = baseDomain + "/" + longURLConvertToShort.get(longURL);
        } else {
            shortURL = baseDomain + "/" + buildShortURL(longURL);
        }

        return shortURL;
    }

}
