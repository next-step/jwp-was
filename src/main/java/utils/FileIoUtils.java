package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileIoUtils {
    private FileIoUtils() {}

    private static final String EMPTY_BODY = "";

    public static byte[] loadFileFromClasspath(String filePath) throws URISyntaxException, IOException {
        if (null != FileIoUtils.class.getClassLoader().getResource(filePath)) {
            return Files.readAllBytes(Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI()));
        }
        return EMPTY_BODY.getBytes();
    }
}
