package webserver.domain;

import org.springframework.http.HttpMethod;

public class RequestLine {
    public static final String QUERY_DELIMITER = "\\?";
    public static final String DELIMITER = " ";
    public static final int PATH_POINT = 1;
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

        HttpMethod httpMethod = HttpMethod.valueOf(attributes[METHOD_POINT]);
        Path path = new Path(attributes[PATH_POINT].split(QUERY_DELIMITER)[0]);
        Protocol protocol = Protocol.newInstance(attributes[PROTOCOL_AND_VERSION_POINT]);

        return new RequestLine(httpMethod, path, protocol);
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

    public String getPathStr() {
        return path.getPathStr();
    }

    @Override
    public String toString() {
        return method.name()+ " " + path + " " + protocol;
    }
}
