package webserver;

import utils.FileIoUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager {

    private static Map<String, String> resources = new HashMap<>();

    public static void addResource(String contentType, String resourcePath) {
        resources.put(contentType, resourcePath);
    }

    public static byte[] get(String accept, String path) throws IOException, URISyntaxException {
        try {
            Path foundPath = Paths.get(findResoucePath(accept), path);
            return FileIoUtils.loadFileFromClasspath(foundPath.toString());
        } catch (NullPointerException e) {
            throw new FileNotFoundException(path);
        }
    }

    public static String matchContentType(String accept) {
        return resources.keySet()
                .stream()
                .filter(key -> accept.contains(key))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    private static String findResoucePath(String accept) {
        return resources.keySet()
                .stream()
                .filter(key -> accept.contains(key))
                .map(key -> resources.get(key))
                .findFirst()
                .orElseThrow(NullPointerException::new);
    }
}
