package utils;

import webserver.http.exception.NotFoundException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    public static byte[] loadFileFromClasspath(String filePath) throws URISyntaxException, IOException {
        URL resource = FileIoUtils.class.getClassLoader().getResource(filePath);
        if (resource == null) {
            throw new NotFoundException("페이지를 찾을수 없습니다.");
        }
        Path path = Paths.get(resource.toURI());
        return Files.readAllBytes(path);
    }
}
