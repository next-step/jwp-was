package webserver.request;

import java.util.regex.Pattern;

public class HttpURI {

    private static final String SEPARATOR = "?";
    private static final int INDEX_OF_PATH = 0;
    private static final int INDEX_OF_QUERY = 1;

    private QueryString queryString;
    private String path;

    private HttpURI(String path, QueryString queryString) {
        this.path = path;
        this.queryString = queryString;
    }

    static HttpURI of(String url) {
        if (url.contains(SEPARATOR)) {
            String[] content = url.split(Pattern.quote(SEPARATOR));
            return new HttpURI(content[INDEX_OF_PATH], QueryString.of(content[INDEX_OF_QUERY]));
        }
        return new HttpURI(url, QueryString.EMPTY);
    }

    String getPath() {
        return path;
    }

    String getRequestParam(String attribute) {
        return queryString.get(attribute);
    }

    boolean matchPath(String path) {
        return this.path.equals(path);
    }

    boolean containPath(String path) {
        return this.path.contains(path);
    }

    @Override
    public String toString() {
        return "HttpURI{" +
                "queryString=" + queryString +
                ", path='" + path + '\'' +
                '}';
    }
}
