package webserver;

import model.http.UriPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ResourceFinder {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public static final String STATIC_RESOURCE_BASE_PATH = "./static";
    public static final String TEMPLATE_RESOURCE_BASE_PATH = "./templates";
    public static final List<String> STATIC_RESOURCE_EXTENSION = Arrays.asList(".css", ".js", ".eot", ".svg", ".ttf", ".woff", ".woff2", ".png");
    public static final List<String> TEMPLATE_RESOURCE_EXTENSION = Arrays.asList(".html", ".ico");

    public static Optional<byte[]> find(UriPath path) {
        return path.getExtension()
                .flatMap(extension -> getResourcePath(path, extension)
                                        .flatMap(FileIoUtils::loadFileFromClasspath));
    }

    private static Optional<String> getResourcePath(UriPath resourcePath, String extension) {
        return getResourcePath(resourcePath.getPath(), extension);
    }

    private static Optional<String> getResourcePath(String resourcePath, String extension) {
        if (STATIC_RESOURCE_EXTENSION.contains(extension)) return Optional.of(STATIC_RESOURCE_BASE_PATH + resourcePath);
        if (TEMPLATE_RESOURCE_EXTENSION.contains(extension)) return Optional.of(TEMPLATE_RESOURCE_BASE_PATH + resourcePath);
        return Optional.empty();
    }
}
