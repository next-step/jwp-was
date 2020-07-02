package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {

    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        URL uri = FileIoUtils.class.getClassLoader().getResource(filePath);
        if (uri == null) {
            return new byte[]{0};
        }
        Path path = Paths.get(uri.toURI());
        return Files.readAllBytes(path);
    }
}
