package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class FileIoUtils {
    public static Optional<byte[]> loadFileFromClasspath(String filePath)
            throws IOException, URISyntaxException {
        URL resource = FileIoUtils.class.getClassLoader().getResource(filePath);
        if (resource != null) {
            Path path = Paths.get(resource.toURI());
            return Optional.of(Files.readAllBytes(path));
        }
        return Optional.empty();
    }
}
