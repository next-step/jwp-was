package utils;

/**
 * Created by hspark on 2019-08-05.
 */
public final class FileUtils {

    public static final String EXTENSION_DELIMITER = ".";

    public static String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf(EXTENSION_DELIMITER));
    }

    public static boolean hasComma(String filename) {
        return filename.contains(EXTENSION_DELIMITER);
    }

}
