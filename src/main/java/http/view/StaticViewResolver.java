package http.view;

import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StaticViewResolver implements ViewResolver {

    private static final String ROOT_OF_STATIC_DIR = "./static";

    @Override
    public View resolve(String viewName) {
        try {
            final Path filepath = Paths.get(ROOT_OF_STATIC_DIR, viewName);
            final byte[] bytes = FileIoUtils.loadFileFromClasspath(filepath.toString());
            return new StaticView(bytes);
        } catch (IOException | URISyntaxException | NullPointerException e) {
            return new StaticView(null);
        }
    }
}
