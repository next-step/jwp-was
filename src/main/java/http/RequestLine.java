package http;

import org.springframework.http.HttpMethod;


import java.util.Objects;

public class RequestLine {

    private final String method;
    private final String path;
    private final String protocol;
    private final String version;

    private RequestLine(String method, String path, String protocol, String version) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
        this.version = version;
    }

    public static RequestLine from(String fullRequestLine) {
        String[] splitedRequestLine = fullRequestLine.split(" ");
        String[] splitedProtocol = splitedRequestLine[2].split("/");
        return new RequestLine(splitedRequestLine[0], splitedRequestLine[1], splitedProtocol[0], splitedProtocol[1]);
    }

    public static RequestLine of(String method, String path, String protocol, String version) {
        return new RequestLine(method, path, protocol, version);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return Objects.equals(method, that.method) &&
                Objects.equals(path, that.path) &&
                Objects.equals(protocol, that.protocol) &&
                Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, path, protocol, version);
    }
}
