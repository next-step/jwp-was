package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TemplateReader {

    private static final String ROOT_OF_TEMPLATE_DIR = "./templates";

    public static byte[] read(String path) throws FileNotFoundException {
        final Path filepath = Paths.get(ROOT_OF_TEMPLATE_DIR, path);
        try {
            return FileIoUtils.loadFileFromClasspath(filepath.toString());
        } catch (IOException | URISyntaxException | NullPointerException e) {
            throw new FileNotFoundException("No such file in template directory!");
        }
    }
}
