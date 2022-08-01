package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {

    private static final String RESOURCE_PATH_PREFIX = "./static";
    private static final String TEMPLATE_PATH_PREFIX = "./templates";

    public static byte[] loadFileFromClasspath(String filePath, boolean isRequestForTemplate) throws IOException, URISyntaxException {
        ClassLoader classLoader = FileIoUtils.class.getClassLoader();
        String pathValue = getPath(filePath, isRequestForTemplate);
        Path path = Paths.get(classLoader.getResource(pathValue).toURI());
        return Files.readAllBytes(path);
    }

    private static String getPath(String filePath, boolean isRequestForTemplate) {
        if (isRequestForTemplate) {
            return TEMPLATE_PATH_PREFIX + filePath;
        }

        return RESOURCE_PATH_PREFIX + filePath;
    }

}
