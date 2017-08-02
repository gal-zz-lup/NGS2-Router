package util;

import models.UserInfo;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import play.Logger;

import java.io.*;
import java.util.List;

/**
 * Created by anuradha_uduwage on 7/26/17.
 */
public class CSVReader extends AbstractFileReader implements Closeable {

  private CSVParser csvParser;

  public CSVReader(InputStream inputStream) {

    super();
    CSVFormat format = CSVFormat.DEFAULT.withHeader().withDelimiter(',');
    try {
      this.csvParser = new CSVParser(new InputStreamReader(inputStream), format);
    } catch (UnsupportedEncodingException ex) {
      Logger.error("Exception occured::: " + ex.getMessage());
      ex.printStackTrace();
    } catch (IOException ex) {
      Logger.error("Exception occured::: " + ex.getMessage());
    }
  }

  @Override
  public void parseFile(InputStream inputStream) {

    UserInfo userInfo = new UserInfo();
    for (CSVRecord csvRecord : csvParser) {
      userInfo.setGallupId(csvRecord.get(CSVHeaders.USER_ID));
      userInfo.setLanguage(csvRecord.get(CSVHeaders.LANGUAge));
      userInfo.save();
    }
  }

  @Override
  public void close() throws IOException {
    this.csvParser.close();
  }
}
