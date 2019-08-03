package webserver;

import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestUri {
    public static final String PATH_QUERY_SPLITTER_CHAR = "?";
    public static final String PATH_QUERY_SPLITTER = "\\" + PATH_QUERY_SPLITTER_CHAR;
    public static final Pattern PATTERN = Pattern.compile("(" + Path.PATTERN + ")" + PATH_QUERY_SPLITTER + "?" + Query.PATTERN);

    private Path path;
    private Query query;

    private RequestUri(Path path, Query query) {
        this.path = path;
        this.query = query;
    }

    private RequestUri(Path path) {
        this.path = path;
    }

    public static RequestUri of(String requestUri) {
        if (!validatePattern(requestUri)) throw new IllegalArgumentException("wrong request uri pattern");

        return hasPathQuerySplitter(requestUri)
                .map(uri -> {
                    String[] parts = uri.split(PATH_QUERY_SPLITTER);
                    Path path = Path.of(parts[0]);
                    Query query = Query.of(parts[1]);
                    return new RequestUri(path, query);
                }).orElseGet(() -> new RequestUri(Path.of(requestUri)));
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
        return Objects.equals(path, that.path) &&
                Objects.equals(query, that.query);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, query);
    }
}
