package webserver.http;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpPath {
    private static final String PATH_REGEX = "^/\\w*";
    private static final Pattern PATTERN = Pattern.compile(PATH_REGEX);
    private static final String QUERYSTRING_START_PREFIX = "?";

    private final String path;
    private final HttpParams httpParams;

    public HttpPath(String path) {
        validate(path);
        this.httpParams = parseQuerystring(path);
        this.path = path;
    }

    private HttpParams parseQuerystring(String querystring) {
        if (querystring.contains(QUERYSTRING_START_PREFIX)) {
            return new HttpParams(querystring.substring(querystring.indexOf(QUERYSTRING_START_PREFIX)));
        }
        return new HttpParams();
    }

    private void validate(String path) {
        Matcher matcher = PATTERN.matcher(path);
        if (!matcher.find()) {
            throw new IllegalArgumentException("invalid path: " + path);
        }
    }

    public HttpParams getHttpParams() {
        return httpParams;
    }
}
