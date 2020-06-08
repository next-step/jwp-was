package testutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FileLoader {
    private static final String TEST_DIRECTORY = "./src/test/resources/";

    public static InputStream load(final String path) {
        try {
            return new FileInputStream(new File(TEST_DIRECTORY + path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
