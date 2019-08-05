package webserver.http;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Uri {
    private static final String HTTP_PATTERN = "(?i)(http|https):\\/\\/";
    private static final String HOST_PATTERN = "([^\\[\\]:\\/\\?#]+)";
    private static final String PORT_PATTERN = "(?::(\\d*))?";
    private static final String PATH_PATTERN = "(\\/[^?#]*)?";
    private static final String QUERY_PATTERN = "(?:\\?([^#]*))?";
    private static final Pattern URI_PATTERN = Pattern.compile(
            "^(?:" + HTTP_PATTERN + HOST_PATTERN + PORT_PATTERN + ")?" + PATH_PATTERN + QUERY_PATTERN + "$"
    );

    private final String scheme;
    private final String host;
    private final String port;
    private final String path;
    private final Query query;

    private Uri(final String scheme, final String host, final String port, final String path, final Query query) {
        this.scheme = scheme;
        this.host = host;
        this.port = port;
        this.path = path;
        this.query = query;
    }

    static Uri parse(final String field) {
        final Matcher matcher = URI_PATTERN.matcher(field);
        if (matcher.matches()) {
            return new Uri(
                    matcher.group(1),
                    matcher.group(2),
                    matcher.group(3),
                    matcher.group(4),
                    getQuery(matcher.group(5))
            );
        }
        throw new ParseException();
    }

    String getPath() {
        return path;
    }

    String getQuery() {
        if (query == null) {
            return null;
        }
        return query.toString();
    }

    Map<String, String> getQueryParams() {
        return query.getQueryParams();
    }

    private static Query getQuery(final String queryString) {
        if (queryString == null) {
            return null;
        }
        return Query.parse(queryString);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        if (scheme != null) {
            builder.append(scheme);
            builder.append("://");
            builder.append(host);
        }
        if (port != null) {
            builder.append(':');
            builder.append(port);
        }
        if (path != null) {
            builder.append(path);
        }
        if (query != null) {
            builder.append('?');
            builder.append(query);
        }
        return builder.toString();
    }
}
