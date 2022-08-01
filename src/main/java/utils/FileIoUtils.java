package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
        return Files.readAllBytes(path);
    }

    public static boolean existsFileFromClassPath(String filePath) throws URISyntaxException {
        URL resource = FileIoUtils.class.getClassLoader().getResource(filePath);

        if (resource == null) {
            return false;
        }

        return Path.of(resource.toURI()).toFile().exists();
    }
}
