package support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public final class FileSupporter {

    private static final String PATH_FORMAT = "./src/test/resources/%s";

    private FileSupporter() { }

    public static InputStream read(final String filename) throws FileNotFoundException {
        return new FileInputStream(file(filename));
    }

    public static OutputStream write(final String filename) throws FileNotFoundException {
        return new FileOutputStream(file(filename));
    }

    private static File file(final String filename) {
        return new File(String.format(PATH_FORMAT, filename));
    }

}
