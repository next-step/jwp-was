package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class FileIoUtils {
    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(FileIoUtils.class.getClassLoader().getResource(filePath).toURI()));
        return Files.readAllBytes(path);
    }
}
