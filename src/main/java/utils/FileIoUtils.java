package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {

    private static final String RESOURCE_FOLDER_PATH_PREFIX = ".";

    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        ClassLoader classLoader = FileIoUtils.class.getClassLoader();
        Path path = Paths.get(classLoader.getResource(RESOURCE_FOLDER_PATH_PREFIX + filePath).toURI());
        return Files.readAllBytes(path);
    }

}
