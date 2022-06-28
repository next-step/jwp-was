package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    public static String getResourcePath(String path) {
        if (path.indexOf(".ico") != -1 || path.indexOf(".html") != -1) {
            return "./templates" + path;
        }
        return "./static" + path;
    }
    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
        return Files.readAllBytes(path);
    }
}
