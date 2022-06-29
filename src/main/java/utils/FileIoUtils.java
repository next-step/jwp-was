package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    public static byte[] loadFileFromClasspath(String filePath) {
        Path path = null;
        try {
            path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
