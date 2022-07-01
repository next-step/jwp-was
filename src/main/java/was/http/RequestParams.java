package was.http;

import java.util.Map;

public interface RequestParams {
    Map<String, String> getParameters();

    String getParameter(String name);
}
