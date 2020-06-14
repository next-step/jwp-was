package utils;

import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    @Nonnull
    public static byte[] loadFileFromClasspath(@Nullable String filePath) throws IOException, URISyntaxException {
        if (StringUtils.isEmpty(filePath)) {
            return new byte[0];
        }
        URL resource = FileIoUtils.class.getClassLoader().getResource(filePath);
        if (resource == null) {
            return new byte[0];
        }

        Path path = Paths.get(resource .toURI());
        return Files.readAllBytes(path);
    }
}
