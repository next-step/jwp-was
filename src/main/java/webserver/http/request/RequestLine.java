package webserver.http.request;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RequestLine {
    private static final String REQUEST_LINE_DELIMITER = " ";
    private static final int MAXIMUM_PARSED_SIZE = 5;
    private static final Pattern REQUEST_LINE_PATTERN;

    static {
        String httpMethodGroupRegex = "(" + String.join("|", HttpMethod.getValues()) + ")";
        String keyValueRegex = ".*" + Parameters.KEY_VALUE_DELIMITER + ".*";
        String queryStringGroupRegex = "((?:" + keyValueRegex + ")(?:" + Parameters.QUERY_STRING_DELIMITER + keyValueRegex + ")*)";
        String pathGroupRegex = Path.PATH_DELIMITER + "(" + "[^?]*)\\??" + queryStringGroupRegex + "?";
        String protocolGroupRegex = "(\\w+)/([\\d.]+)";
        REQUEST_LINE_PATTERN = Pattern.compile(String.join(REQUEST_LINE_DELIMITER, List.of(httpMethodGroupRegex, pathGroupRegex, protocolGroupRegex)));
    }

    private final HttpMethod method;
    private final Path path;
    private final Protocol protocol;

    private RequestLine(HttpMethod method, Path path, Protocol protocol) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
    }

    private static List<String> parse(String requestLine) {
        Matcher matcher = REQUEST_LINE_PATTERN.matcher(requestLine);
        if (!matcher.find()) {
            throw new IllegalArgumentException("형식에 맞지 않는 요청입니다.");
        }

        return IntStream.rangeClosed(1, MAXIMUM_PARSED_SIZE).mapToObj(matcher::group).collect(Collectors.toList());
    }

    public static RequestLine from(String requestLine) {
        List<String> parsed = parse(requestLine);
        boolean hasQueryString = parsed.size() >= MAXIMUM_PARSED_SIZE;
        return new RequestLine(HttpMethod.from(parsed.get(0)), new Path(parsed.get(1), Parameters.from(hasQueryString ? parsed.get(2) : null)), new Protocol(parsed.get(hasQueryString ? 3 : 2), new Version(parsed.get(hasQueryString ? 4 : 3))));
    }

    public HttpMethod getMethod() {
        return method;
    }

    public Path getPath() {
        return path;
    }

    public Protocol getProtocol() {
        return protocol;
    }
}
