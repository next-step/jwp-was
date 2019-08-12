package webserver.http.request;

import java.util.Map;

public class RequestUri {

    private static final String PATH_SEPARATOR = "\\?";
    private static final String VIEW_SEPARATOR = "\\.";
    private static final String HTML = "html";

    private String path;
    private QueryParam queryParams;

    private RequestUri(String path) {
        this.path = path;
        this.queryParams = QueryParam.EMPTY_QUERY_PARAM;
    }

    private RequestUri(String path, String queryString) {
        this.path = path;
        this.queryParams = QueryParam.parse(queryString);
    }

    public static RequestUri parse(String line) {
        String[] split = line.split(PATH_SEPARATOR);

        String path = toPath(split[0]);

        if (!hasQueryParams(split)) {
            return new RequestUri(path);
        }

        return new RequestUri(path, split[1]);
    }

    private static String toPath(String path) {
        if (hasView(path)) {
            path = path.split(VIEW_SEPARATOR)[0];
        }
        return path;
    }

    private static boolean hasView(String path) {
        return path.contains(HTML);
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

    private static boolean hasQueryParams(String[] split) {
        return split.length > 1;
    }
}
