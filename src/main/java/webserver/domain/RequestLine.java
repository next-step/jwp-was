package webserver.domain;

import org.springframework.http.HttpMethod;

public class RequestLine {
    public static final String DELIMITER = " ";
    public static final int PATH_AND_QUERYSTRING_POINT = 1;
    public static final int PROTOCOL_AND_VERSION_POINT = 2;
    public static final int METHOD_POINT = 0;

    private HttpMethod method;
    private Protocol protocol;
    private Path path;

    public RequestLine() {
    }

    public RequestLine(HttpMethod method, Path path, Protocol protocol) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
    }

    public static RequestLine from(String line) {
        if (line == null || line.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 RequestLine입니다. valeu:" + line);
        }

        String[] attributes = line.split(DELIMITER);

        Path path = Path.from(attributes[PATH_AND_QUERYSTRING_POINT]);
        Protocol protocol = Protocol.newInstance(attributes[PROTOCOL_AND_VERSION_POINT]);

        return new RequestLine(HttpMethod.valueOf(attributes[METHOD_POINT]), path, protocol);
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


    public Parameters getParameters() {
        return path.getParameters();
    }
}
