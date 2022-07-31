package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileIoUtils.class);
    private static final String CLASS_PATH = "./templates";

    public static byte[] loadFileFromClasspath(String filePath) {
        try {
            Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(CLASS_PATH + (filePath)).toURI());

            return Files.readAllBytes(path);
        } catch (IOException e) {
            logger.error(e.getMessage());

            return new byte[0];
        } catch (URISyntaxException e) {
            logger.error(e.getMessage());

            return new byte[0];
        }
    }
}
