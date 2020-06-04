package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    private static final String PREFIX_TEMPLATES = "./templates";

    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        filePath = PREFIX_TEMPLATES + filePath;
        Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());

        return Files.readAllBytes(path);
    }
}
