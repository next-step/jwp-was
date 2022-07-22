package http.httprequest.requestline;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestLine {


    private static final Pattern HTTP_REQUEST_PATTERN = Pattern.compile("(?<method>.+) (?<path>.+) (?<protocol>.+)");
    private static final String REGEX_GROUP_METHOD = "method";
    private static final String REGEX_GROUP_PATH = "path";
    private static final String REGEX_GROUP_PROTOCOL = "protocol";

    private final HttpMethod httpMethod;
    private final Path path;
    private final Protocol protocol;

    public RequestLine(HttpMethod httpMethod,
                       Path path,
                       Protocol protocol) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.protocol = protocol;
    }

    public static RequestLine from(String requestLine) {
        Matcher matcher = HTTP_REQUEST_PATTERN.matcher(requestLine);

        if (!matcher.find()) {
            throw new IllegalArgumentException("HTTP REQUEST 형식에 맞지 않습니다. request: " + requestLine);
        }

        String method = matcher.group(REGEX_GROUP_METHOD);
        Path path = Path.from(matcher.group(REGEX_GROUP_PATH));
        Protocol protocol = Protocol.from(matcher.group(REGEX_GROUP_PROTOCOL));

        return new RequestLine(HttpMethod.valueOf(method), path, protocol);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return httpMethod == that.httpMethod && path.equals(that.path) && protocol.equals(that.protocol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpMethod, path, protocol);
    }
}
