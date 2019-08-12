package webserver.template;

import webserver.ResourceLoader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class HtmlViewResolver implements ViewResolver {

    @Override
    public byte[] loadView(String path) throws IOException, URISyntaxException {
        return loadHtml(path);
    }

    @Override
    public byte[] loadView(String path, Map<String, Object> data) throws IOException, URISyntaxException {
       return loadHtml(path);
    }

    private byte[] loadHtml(String path) throws IOException, URISyntaxException {
        String resourcePath = ResourceLoader.getResourcePath(path);
        byte[] body = ResourceLoader.loadResource(resourcePath);

        return body;
    }
}
