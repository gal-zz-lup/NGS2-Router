package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by anuradha_uduwage on 7/14/17.
 */
public  abstract class AbstractFileReader {

    /**
     * Method to upload the the file
     * @param file upload file
     * @return
     * @throws FileNotFoundException
     */
    public InputStream uploadFile(File file) throws FileNotFoundException {
        InputStream inputDataStream = new FileInputStream(file);
        return inputDataStream;
    }

    /**
     * Abstract method to parse the data stream.
     * @param inputStream input data from the file.
     * @return
     */
    abstract public List<Object> parseFile(InputStream inputStream);

}
