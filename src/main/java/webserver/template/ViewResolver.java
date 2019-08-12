package webserver.template;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public interface ViewResolver {
    byte[] loadView(String path) throws IOException, URISyntaxException;
    byte[] loadView(String path, Map<String, Object> data) throws IOException, URISyntaxException;
}
