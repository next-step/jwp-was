package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileIoUtils {
    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        if (null == FileIoUtils.class.getClassLoader().getResource(filePath)) {
            return "".getBytes();
        }
        return Files.readAllBytes(Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI()));
    }
}
