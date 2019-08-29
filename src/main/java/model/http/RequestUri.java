package model.http;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestUri {
    public static final String PATH_QUERY_SPLITTER_CHAR = "?";
    public static final String PATH_QUERY_SPLITTER = "\\" + PATH_QUERY_SPLITTER_CHAR;
    public static final Pattern PATTERN = Pattern.compile("(" + UriPath.PATTERN + ")" + PATH_QUERY_SPLITTER + "?" + Query.PATTERN);

    private UriPath uriPath;
    private Query query;

    public UriPath getUriPath() {
        return uriPath;
    }

    public Query getQuery() {
        return query;
    }

    private RequestUri(UriPath uriPath, Query query) {
        this.uriPath = uriPath;
        this.query = query;
        if (query == null) {
            this.query = Query.ofEmpty();
        }
    }

    private RequestUri(UriPath uriPath) {
        this.uriPath = uriPath;
        this.query = Query.ofEmpty();
    }

    public static RequestUri of(String requestUri) {
        requestUri = requestUri.trim();
        if (!validatePattern(requestUri)) throw new IllegalArgumentException("wrong request uri pattern");

        String finalRequestUri = requestUri;
        return hasPathQuerySplitter(requestUri)
                .map(uri -> {
                    String[] parts = uri.split(PATH_QUERY_SPLITTER);
                    UriPath uriPath = UriPath.of(parts[0]);
                    Query query = Query.of(parts[1]);
                    return new RequestUri(uriPath, query);
                }).orElseGet(() -> new RequestUri(UriPath.of(finalRequestUri)));
    }

    public static RequestUri of(UriPath uriPath, Query query) {
        return new RequestUri(uriPath, query);
    }

    private static Optional<String> hasPathQuerySplitter(String requestUri) {
        if (requestUri.contains(PATH_QUERY_SPLITTER_CHAR)) return Optional.of(requestUri);
        return Optional.empty();
    }

    private static boolean validatePattern(String requestUri) {
        Matcher matcher = PATTERN.matcher(requestUri);
        return matcher.find() && requestUri.length() == matcher.group().length();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestUri that = (RequestUri) o;
        return Objects.equals(uriPath, that.uriPath) &&
                Objects.equals(query, that.query);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uriPath, query);
    }

    @Override
    public String toString() {
        return "RequestUri{" +
                "uriPath=" + uriPath +
                ", query=" + query +
                '}';
    }

    public RequestUri appendQuery(Query query) {
        this.query.addAll(query);
        return this;
    }
}
