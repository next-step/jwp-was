package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        final URL resource = FileIoUtils.class.getClassLoader().getResource(filePath);
        if (resource == null) {
            return "".getBytes(StandardCharsets.UTF_8);
        }

        Path path = Paths.get(resource.toURI());
        return Files.readAllBytes(path);
    }
}
