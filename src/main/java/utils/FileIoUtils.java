package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class FileIoUtils {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String CLASSPATH_TEMPLATE = "./templates";
    private static final String CLASSPATH_STATIC = "./static";

    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        String changePath = changePath(filePath);
        logger.debug("현재 파일 경로 : {}", changePath);

        Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(changePath).toURI());
        return Files.readAllBytes(path);
    }

    private static String changePath(String path) {
        if (StaticFile.isStatic(path)) {
            return CLASSPATH_STATIC + path;
        }
        return CLASSPATH_TEMPLATE + path;
    }

}
