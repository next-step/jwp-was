package webserver.request;

import java.util.Objects;

public class RequestLine {
    private final HttpMethod httpMethod;
    private final Path path;
    private final Protocol protocol;

    private RequestLine(HttpMethod httpMethod, Path path, Protocol protocol) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.protocol = protocol;
    }

    public static RequestLine from(String readLine) {
        String[] lineSplit = readLine.split(" ");
        HttpMethod httpMethod = HttpMethod.from(lineSplit[0]);
        Path path = Path.from(lineSplit[1]);
        Protocol protocol = Protocol.from(lineSplit[2]);
        return new RequestLine(httpMethod, path, protocol);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequestLine)) return false;
        RequestLine that = (RequestLine) o;
        return httpMethod == that.httpMethod &&
                Objects.equals(path, that.path) &&
                Objects.equals(protocol, that.protocol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpMethod, path, protocol);
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public Path getPath() {
        return path;
    }

    public Protocol getProtocol() {
        return protocol;
    }
}
