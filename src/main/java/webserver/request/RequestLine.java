package webserver.request;

import java.util.Objects;

public class RequestLine {
    private final HttpMethod httpMethod;
    private final Url url;
    private final Protocol protocol;

    private RequestLine(HttpMethod httpMethod, Url url, Protocol protocol) {
        this.httpMethod = httpMethod;
        this.url = url;
        this.protocol = protocol;
    }

    public static RequestLine from(String readLine) {
        String[] lineSplit = readLine.split(" ");
        HttpMethod httpMethod = HttpMethod.from(lineSplit[0]);
        Url url = Url.from(lineSplit[1]);
        Protocol protocol = Protocol.from(lineSplit[2]);
        return new RequestLine(httpMethod, url, protocol);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequestLine)) return false;
        RequestLine that = (RequestLine) o;
        return httpMethod == that.httpMethod &&
                Objects.equals(url, that.url) &&
                Objects.equals(protocol, that.protocol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpMethod, url, protocol);
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public Url getPath() {
        return url;
    }

    public Protocol getProtocol() {
        return protocol;
    }
}
