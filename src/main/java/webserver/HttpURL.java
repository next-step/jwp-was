package webserver;

import java.util.regex.Pattern;

public class HttpURL {

    private static final String SEPARATOR = "?";
    private String path;
    private QueryString queryString;

    private HttpURL(String path, QueryString queryString) {
        this.path = path;
        this.queryString = queryString;
    }

    static HttpURL of(String path) {
        if (path.contains(SEPARATOR)) {
            String[] content = path.split(Pattern.quote(SEPARATOR));
            return new HttpURL(content[0], QueryString.of(content[1]));
        }
        return new HttpURL(path, QueryString.EMPTY);
    }

    String getPath() {
        return path;
    }

    String getRequestParam(String attribute) {
        return queryString.get(attribute);
    }
}
