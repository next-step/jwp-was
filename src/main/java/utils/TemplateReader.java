package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TemplateReader {

    private static final String ROOT_OF_TEMPLATE_DIR = "./templates";
    private static final String ROOT_OF_STATIC_DIR = "./static";

    public static byte[] read(String path) throws FileNotFoundException {
        final Path filepath = Paths.get(determineRootPath(path), path);
        try {
            return FileIoUtils.loadFileFromClasspath(filepath.toString());
        } catch (IOException | URISyntaxException | NullPointerException e) {
            throw new FileNotFoundException("No such file in template directory!");
        }
    }

    private static String determineRootPath(String path) {
        if (path.contains("css") || path.contains("fonts") || path.contains("images") || path.contains("js")) {
            return ROOT_OF_STATIC_DIR;
        }
        return ROOT_OF_TEMPLATE_DIR;
    }
}
