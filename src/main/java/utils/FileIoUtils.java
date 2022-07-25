package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
        return Files.readAllBytes(path);
    }

    public static String getFileExtension(final String location) {
        final int lastIndexOf = location.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return location.substring(lastIndexOf + 1);
    }
}
