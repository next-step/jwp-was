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
    private static final String PREFIX_TEMPLATES = "./templates";
    private static final String PREFIX_STATIC = "./static";

    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        if (filePath.endsWith(".js") || filePath.endsWith(".css")
                || filePath.endsWith(".ttf") || filePath.endsWith(".woff")) {
            filePath = PREFIX_STATIC + filePath;
            logger.debug("js or css filePath: {}", filePath);
        } else {
            filePath = PREFIX_TEMPLATES + filePath;
            logger.debug("not js or css filePath: {}", filePath);
        }
        Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
        logger.debug("path : {}", path);
        return Files.readAllBytes(path);
    }
}