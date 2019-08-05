package webserver.http.request;

import utils.StringUtils;

public class RequestURL {

    private static final String SEPARATOR = "?";

    private static final int SEPARATOR_NOT_FOUND_INDEX = -1;
    private static final int START_INDEX = 0;

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

        final int separatorIndex = parseSeparatorIndex(rawRequestURL);
        if (separatorIndex == SEPARATOR_NOT_FOUND_INDEX) {
            return new RequestURL(RequestPath.of(rawRequestURL), RequestQuery.EMPTY);
        }

        final RequestPath requestPath = parseRequestPath(rawRequestURL, separatorIndex);
        final RequestQuery requestQuery = parseRequestQuery(rawRequestURL, separatorIndex);

        return new RequestURL(requestPath, requestQuery);
    }

    String getPath() {
        return requestPath.getRequestPath();
    }

    String getParameter(final String key) {
        return requestQuery.getString(key);
    }

    RequestQuery getParameters() {
        return requestQuery;
    }

    private static int parseSeparatorIndex(final String rawRequestURL) {
        return rawRequestURL.indexOf(SEPARATOR);
    }

    private static RequestPath parseRequestPath(final String rawRequestURL,
                                                final int separatorIndex) {
        final String rawRequestPath = rawRequestURL.substring(START_INDEX, separatorIndex);
        return RequestPath.of(rawRequestPath);
    }

    private static RequestQuery parseRequestQuery(final String rawRequestURL,
                                                  final int separatorIndex) {
        final String rawRequestQuery = rawRequestURL.substring(separatorIndex + SEPARATOR.length());
        return RequestQuery.of(rawRequestQuery);
    }

    @Override
    public String toString() {
        return "RequestURL{" +
                "requestPath=" + requestPath +
                ", requestQuery=" + requestQuery +
                '}';
    }
}
