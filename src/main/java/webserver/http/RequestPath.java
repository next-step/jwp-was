package webserver.http;

import com.github.jknack.handlebars.internal.lang3.StringUtils;

public class RequestPath {

    private static final String REQUEST_PATH_DELIMITER = "\\?";
    private static final int REQUEST_PATH_VALUE_COUNT = 2;

    private String path;
    private QueryString queryString;

    private RequestPath(String path, QueryString queryString) {
        this.path = path;
        this.queryString = queryString;
    }

    public static RequestPath parse(String requestPathValue) {
        String [] values = requestPathValue.split(REQUEST_PATH_DELIMITER);

        String path = values[0];
        String queryString = StringUtils.EMPTY;

        if(hasQueryString(values.length))
            queryString = values[1];

        return new RequestPath(path, QueryString.parse(queryString));
    }

    private static boolean hasQueryString(int size) {
        return size == REQUEST_PATH_VALUE_COUNT;
    }

    public String getPath() {
        return path;
    }

    public QueryString getQueryString() {
        return queryString;
    }
}
