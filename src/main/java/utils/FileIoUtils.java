package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {

    public static final String VIEW_RESOURCES_PATH = "./templates";
    public static final String STATIC_RESOURCES_PATH = "./static";

    public static byte[] loadFileFromClasspath(String resourcesPath, String filePath) throws IOException, URISyntaxException {
        Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(resourcesPath + filePath).toURI());
        return Files.readAllBytes(path);
    }
}
