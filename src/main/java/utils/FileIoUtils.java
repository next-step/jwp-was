package utils;

import exception.ResourceNotFoundException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        URL resource = FileIoUtils.class.getClassLoader().getResource(filePath);
        if (resource == null) {
            throw new ResourceNotFoundException(filePath);
        }
        Path path = Paths.get(resource.toURI());
        return Files.readAllBytes(path);
    }
}
