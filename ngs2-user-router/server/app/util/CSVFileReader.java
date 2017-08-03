package util;

import models.UserInfo;
import play.Logger;
import play.mvc.WebSocket;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anuradha_uduwage on 7/16/17.
 */
@Deprecated
public class CSVFileReader extends AbstractFileReader {

    private static CSVFileReader csvFileReader;

    public CSVFileReader() {}

    public static CSVFileReader getReaderInstance() {
        if (csvFileReader == null) {
            csvFileReader = new CSVFileReader();
        }
        return csvFileReader;
    }

    /**
     * Method implements the csv file parser and return list of records.
     * @param inputStream input data from the file.
     * @return
     */
    @Override
    public void parseFile(InputStream inputStream) {
        Logger.info("Getting reading to parse the file");

        UserInfo userInfo = new UserInfo();

        BufferedReader bufferedReader;
        List<Object> recordList = null;

        try {
            String line = null;
            recordList = new ArrayList<>();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = bufferedReader.readLine()) != null) {
                recordList.add(line);
            }
            bufferedReader.close();
        } catch (UnsupportedEncodingException ex) {
            Logger.error("Exception occured::: " + ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.error("Exception occured::: " + ex.getMessage());
        }
    }

}
