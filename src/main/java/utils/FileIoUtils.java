package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class FileIoUtils {
    private static final String DEFAULT_STATIC_PATH = "./static";

    public static Optional<byte[]> loadFileFromClasspath(final String filePath) throws IOException, URISyntaxException {
        final URL url = FileIoUtils.class.getClassLoader().getResource(DEFAULT_STATIC_PATH + filePath);
        if (url != null) {
            final Path path = Paths.get(url.toURI());
            return Optional.of(Files.readAllBytes(path));
        }
        return Optional.empty();
    }
}
