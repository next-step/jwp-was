package http;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import utils.Args;

/**
 * Created by iltaek on 2020/06/03 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class RequestURI {

    private static final String REQUEST_URI_FORMAT = "(?<path>.+)\\?(?<query>.+)";
    private static final Pattern REQUEST_URI_PATTERN = Pattern.compile(REQUEST_URI_FORMAT);
    protected static final String ILLEGAL_URI = "유효하지 않은 RequestURI 입니다.";

    private final String path;
    private final QueryString queryString;

    public RequestURI(String requestURI) {
        if (requestURI.contains("?")) {
            Matcher uriMatcher = Args.checkPattern(REQUEST_URI_PATTERN.matcher(requestURI), ILLEGAL_URI);
            this.path = uriMatcher.group("path");
            this.queryString = QueryString.of(uriMatcher.group("query"));
        } else {
            this.path = requestURI;
            this.queryString = QueryString.of(null);
        }
    }

    public String getPath() {
        return this.path;
    }

    public String getQueryString() {
        return queryString.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RequestURI that = (RequestURI) o;

        if (path != null ? !path.equals(that.path) : that.path != null) {
            return false;
        }
        return queryString != null ? queryString.equals(that.queryString) : that.queryString == null;
    }

    @Override
    public int hashCode() {
        int result = path != null ? path.hashCode() : 0;
        result = 31 * result + (queryString != null ? queryString.hashCode() : 0);
        return result;
    }
}
