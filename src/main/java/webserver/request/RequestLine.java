package webserver.request;

import utils.Assert;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

public final class RequestLine {

    private static final Pattern REQUEST_LINE_DELIMITER_PATTERN = Pattern.compile("[\\s\\t]");
    private static final Pattern REQUEST_URI_PATTERN = Pattern.compile("\\?");
    private static final int METHOD_INDEX = 0;
    private static final int PATH_INDEX = 1;
    private static final int PROTOCOL_INDEX = 2;
    private static final int REQUEST_LINE_SPLIT_COUNT = 3;

    private final HttpMethod method;
    private final Path path;
    private final QueryString query;
    private final Protocol protocol;

    private RequestLine(HttpMethod method, Path path, QueryString query, Protocol protocol) {
        Assert.notNull(method, "'method' must not be null");
        Assert.notNull(path, "'path' must not be null");
        Assert.notNull(query, "'query' must not be null");
        Assert.notNull(protocol, "'protocol' must not be null");
        this.method = method;
        this.path = path;
        this.query = query;
        this.protocol = protocol;
    }

    public static RequestLine from(String request) {
        validate(request);
        return parse(request);
    }

    private static void validate(String request) {
        if (request == null || request.isBlank()) {
            throw new IllegalArgumentException("request must not be empty");
        }
        if (REQUEST_LINE_DELIMITER_PATTERN.split(request).length != REQUEST_LINE_SPLIT_COUNT) {
            throw new IllegalArgumentException(String.format("request(%s) is not request pattern(Method SP Request-URI SP HTTP-Version CRLF)", request));
        }
    }

    private static RequestLine parse(String request) {
        String[] splitRequest = REQUEST_LINE_DELIMITER_PATTERN.split(request, REQUEST_LINE_SPLIT_COUNT);

        HttpMethod method = HttpMethod.valueOf(splitRequest[METHOD_INDEX]);
        Protocol protocol = Protocol.from(splitRequest[PROTOCOL_INDEX]);

        if (doesNotHaveQueryDelimiter(splitRequest[PATH_INDEX])) {
            return new RequestLine(method, Path.from(splitRequest[PATH_INDEX]), QueryString.empty(), protocol);
        }
        String[] pathQuery = REQUEST_URI_PATTERN.split(splitRequest[PATH_INDEX]);
        return new RequestLine(method, Path.from(pathQuery[0]), QueryString.from(pathQuery[1]), protocol);
    }

    private static boolean doesNotHaveQueryDelimiter(String string) {
        return !REQUEST_URI_PATTERN.matcher(string).find();
    }

    HttpMethod method() {
        return method;
    }

    Path path() {
        return path;
    }

    String protocolName() {
        return protocol.name();
    }

    String protocolVersion() {
        return protocol.version();
    }

    QueryString query() {
        return query;
    }

    Optional<String> queryValue(String parameter) {
        return query.value(parameter);
    }

    boolean matchPath(Path path) {
        return this.path.equals(path);
    }

    boolean matchMethod(HttpMethod method) {
        return this.method == method;
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, path, query, protocol);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestLine that = (RequestLine) o;
        return method == that.method && Objects.equals(path, that.path) && Objects.equals(query, that.query) && Objects.equals(protocol, that.protocol);
    }

    @Override
    public String toString() {
        return "RequestLine{" +
                "method=" + method +
                ", path=" + path +
                ", query=" + query +
                ", protocol=" + protocol +
                '}';
    }
}
