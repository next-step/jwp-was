package webserver.http;

import java.util.Map;

public class RequestUri {
    private String path;
    private QueryParam queryParams;

    public RequestUri(String path) {
        this.path = path;
        this.queryParams = QueryParam.EMPTY_QUERY_PARAM;
    }

    public RequestUri(String path, String queryString) {
        this.path = path;
        this.queryParams = QueryParam.parse(queryString);
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getQueryParams() {
        return queryParams.getParameterMap();
    }

    public String getParameter(String key) {
        return queryParams.getParameter(key);
    }

    @Override
    public String toString() {
        return "RequestUri{" +
                "path='" + path + '\'' +
                ", queryParams=" + queryParams +
                '}';
    }
}
