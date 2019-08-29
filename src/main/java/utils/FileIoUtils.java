package utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

public class FileIoUtils {
    public static Optional<byte[]> loadFileFromClasspath(String filePath) {
        try {
            Path path = Paths.get(Objects.requireNonNull(FileIoUtils.class.getClassLoader().getResource(filePath).toURI()));
            return Optional.of(Files.readAllBytes(path));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
