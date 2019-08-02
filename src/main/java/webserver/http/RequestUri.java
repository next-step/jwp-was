package webserver.http;

import java.util.Collections;
import java.util.Map;

public class RequestUri {
    private String path;
    private Map<String, String> queryParams;

    public RequestUri(String path) {
        this.path = path;
        this.queryParams = Collections.emptyMap();
    }

    public RequestUri(String path, Map<String, String> queryParams) {
        this.path = path;
        this.queryParams = queryParams;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }
}
