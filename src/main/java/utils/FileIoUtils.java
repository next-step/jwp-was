package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class FileIoUtils {
    public static Optional<byte[]> loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());

        if (isExistsPath(path)) {
            return Optional.of(Files.readAllBytes(path));
        }

        return Optional.empty();
    }

    public static boolean isExistsPath(Path path) {
        return path != null && path.toFile().exists();
    }
}
