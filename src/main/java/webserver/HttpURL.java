package webserver;

import java.util.regex.Pattern;

public class HttpURL {

    private static final String SEPARATOR = "?";
    private static final int INDEX_OF_PATH = 0;
    private static final int INDEX_OF_QUERY = 1;

    private QueryString queryString;
    private String path;

    private HttpURL(String path, QueryString queryString) {
        this.path = path;
        this.queryString = queryString;
    }

    static HttpURL of(String url) {
        if (url.contains(SEPARATOR)) {
            String[] content = url.split(Pattern.quote(SEPARATOR));
            return new HttpURL(content[INDEX_OF_PATH], QueryString.of(content[INDEX_OF_QUERY]));
        }
        return new HttpURL(url, QueryString.EMPTY);
    }

    String getPath() {
        return path;
    }

    String getRequestParam(String attribute) {
        return queryString.get(attribute);
    }
}
