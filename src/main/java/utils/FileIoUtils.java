package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    public static byte[] loadFileFromClasspath(String filePath)  {
        try {
            var path = fetchPath(filePath);
            return Files.readAllBytes(path);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("해당 URL의 파일을 찾을 수 없습니다. path = " + filePath, e);
        }
    }

    private static Path fetchPath(String filePath) throws URISyntaxException {
        if (filePath.endsWith(".html")) {
            return Paths.get(FileIoUtils.class.getClassLoader().getResource("./templates" + filePath).toURI());

        }
        return Paths.get(FileIoUtils.class.getClassLoader().getResource("./static" + filePath).toURI());
    }
}
