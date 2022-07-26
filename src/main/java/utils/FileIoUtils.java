package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FileIoUtils {

    private static final String classPath = "./templates";
    private static final Set<String> files = new HashSet<>(Arrays.asList("/index.html"));

    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
//        if (!files.contains(filePath)) {
//            return "Hello World".getBytes();
//        }
        Path path = Paths.get(FileIoUtils.class.getClassLoader()
                .getResource(classPath + filePath)
                .toURI());

        return Files.readAllBytes(path);
    }
}
