package http.request;

import java.util.Map;

public interface RequestMethod {
    String getPath();

    String getMethodName();

    Map<String, String> getRequestParameters();
}
