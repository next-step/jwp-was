package support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by hspark on 2019-08-11.
 */
public class TestHttpMessageLoader {
    private static final String TEST_DIRECTORY = "src/test/resources/";

    public static InputStream load(String fileName) throws FileNotFoundException {
        return new FileInputStream(new File(TEST_DIRECTORY + fileName));
    }
}
