package webserver.view;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public interface ViewResolver {
    View render(String viewName) throws IOException, URISyntaxException;
    View render(String viewName, Map<String, Object> data) throws IOException, URISyntaxException;
}
