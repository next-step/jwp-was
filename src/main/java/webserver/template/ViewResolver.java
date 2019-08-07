package webserver.template;

import java.io.IOException;
import java.util.Map;

public interface ViewResolver {
    byte[] loadTemplate(String path, Map<String, Object> data) throws IOException;
}
