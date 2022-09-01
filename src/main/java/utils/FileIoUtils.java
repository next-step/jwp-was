package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileIoUtils.class);
    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        logger.debug("filePath: {}", filePath);
        Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
        return Files.readAllBytes(path);
    }

	public static boolean needsTemplatesPath(String path) {
		return path.endsWith(".html") || path.endsWith(".ico");
	}

	public static boolean needsStaticPath(String path) {
		return path.startsWith("/css") || path.startsWith("/js") || path.startsWith("/font") || path.startsWith("/images");
	}

	public static boolean isStatic(String path) {
		return needsTemplatesPath(path) || needsStaticPath(path);
	}
}
