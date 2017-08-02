package util;

import models.ExperimentInstance;
import models.UserInfo;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anuradha_uduwage.
 */
public class CSVWriter implements Closeable {

  private final CSVPrinter csvPrinter;
  private final File tempFile;

  /**
   * Generate CSV file
   * @param header column header
   */
  public CSVWriter(String... header) {
    CSVFormat format = CSVFormat.DEFAULT.withHeader().withDelimiter(',');
    try {
      this.tempFile = File.createTempFile("user_sample_file", "csv");
    } catch (IOException ex) {
      throw new RuntimeException("Unable to create the temp file ", ex);
    }
    try {
      csvPrinter = new CSVPrinter(new FileWriter(this.tempFile), format);
    } catch (IOException ex) {
      throw new RuntimeException("Unable to initialize the CSV Printer", ex);
    }
  }

  /**
   *
   * @param userInfoList
   */
  public void writeToFile(List<UserInfo> userInfoList) {
    List userInfoRows = new ArrayList();
    ExperimentInstance experimentInstance = new ExperimentInstance();
    for (UserInfo userInfo : userInfoList) {
      userInfoRows.add(userInfo.getUserId());
      userInfoRows.add(userInfo.getGallupId());
      userInfoRows.add(userInfo.getRandomizedId());
      //need to obtain the url
    }
  }

  @Override
  public void close() throws IOException {
    this.csvPrinter.close();
  }

}
