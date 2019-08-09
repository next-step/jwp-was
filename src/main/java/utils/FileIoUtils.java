package utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    public static byte[] loadFileFromClasspath(String filePath) throws Exception {
        Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
        return Files.readAllBytes(path);
    }
}
