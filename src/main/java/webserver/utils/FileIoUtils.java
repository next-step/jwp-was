package webserver.utils;

import webserver.http.domain.exception.ResourceNotFoundException;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class FileIoUtils {
    public static byte[] loadFileFromClasspath(String filePath) {
        URL url = FileIoUtils.class.getClassLoader().getResource(filePath);
        if (Objects.isNull(url)) {
            throw new ResourceNotFoundException("요청하신 리소스가 존재하지 않습니다.");
        }

        try {
            Path path = Paths.get(url.toURI());
            return Files.readAllBytes(path);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean hasFile(String filePath) {
        URL resource = FileIoUtils.class.getClassLoader().getResource(filePath);
        if (Objects.isNull(resource)) {
            return false;
        }
        File file = getFile(resource);
        return file.isFile();
    }

    private static File getFile(@Nonnull URL resource) {
        try {
            return Paths.get(resource.toURI()).toFile();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
