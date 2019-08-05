package webserver.view;

import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class HtmlViewResolver implements ViewResolver {

    private static final String PREFIX = "./templates";
    private static final String SUFFIX = ".html";

    @Override
    public byte[] render(String viewName) throws IOException, URISyntaxException {
        return FileIoUtils.loadFileFromClasspath(PREFIX + viewName + SUFFIX);
    }

    @Override
    public View render(String viewName, Map<String, Object> data) throws IOException, URISyntaxException {
        return null;
    }
}
