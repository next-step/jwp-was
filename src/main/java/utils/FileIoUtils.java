package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {

    private static final String FILE_DIR = "./templates";

    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        final Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(FILE_DIR + filePath).toURI());
        return Files.readAllBytes(path);
    }
}
