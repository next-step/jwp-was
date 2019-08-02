package webserver.http;

import utils.StringUtils;

import java.util.regex.Pattern;

public class RequestURL {

    private static final String SEPARATOR = "?";
    private static final int INDEX_OF_REQUEST_PATH = 0;
    private static final int INDEX_OF_REQUEST_QUERY = 1;

    private final RequestPath requestPath;
    private final RequestQuery requestQuery;

    private RequestURL(final RequestPath requestPath,
                       final RequestQuery requestQuery) {
        this.requestPath = requestPath;
        this.requestQuery = requestQuery;
    }

    public static RequestURL of(final String rawRequestURL) {
        if (StringUtils.isBlank(rawRequestURL)) {
            throw new InvalidRequestURLException(rawRequestURL);
        }

        if (!rawRequestURL.contains(SEPARATOR)) {
            return new RequestURL(RequestPath.of(rawRequestURL), RequestQuery.EMPTY);
        }

        final String[] splitRawRequestURL = rawRequestURL.split(Pattern.quote(SEPARATOR));
        final RequestPath requestPath = RequestPath.of(splitRawRequestURL[INDEX_OF_REQUEST_PATH]);
        final RequestQuery requestQuery = RequestQuery.of(splitRawRequestURL[INDEX_OF_REQUEST_QUERY]);

        return new RequestURL(requestPath, requestQuery);
    }

    String getPath() {
        return requestPath.getRequestPath();
    }

    @Override
    public String toString() {
        return "RequestURL{" +
                "requestPath=" + requestPath +
                ", requestQuery=" + requestQuery +
                '}';
    }
}
