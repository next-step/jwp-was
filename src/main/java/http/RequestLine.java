package http;

import java.util.Objects;

public class RequestLine {

    private final Method method;
    private final RequestUrl requestUrl;
    private final Protocol protocol;

    private RequestLine(Method method, RequestUrl requestUrl, Protocol protocol) {
        this.method = method;
        this.requestUrl = requestUrl;
        this.protocol = protocol;
    }

    public static RequestLine of(String method, String path, Protocol protocol) {
        return new RequestLine(Method.valueOf(method), RequestUrl.of(path), protocol);
    }

    public static RequestLine of(String method, String path, String protocol, String version) {
        return RequestLine.of(method, path, Protocol.of(protocol, version));
    }

    public static RequestLine of(String line) {
        String[] values = line.trim().split(" ");
        Protocol protocol = Protocol.of(values[2]);
        return RequestLine.of(values[0], values[1], protocol);
    }

    public String getPath() {
        return requestUrl.getPath();
    }

    public QueryString getQueryString() {
        return requestUrl.getQueryString();
    }

    public Method getMethod() {
        return this.method;
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
        return Objects.equals(method, that.method) &&
            Objects.equals(requestUrl, that.requestUrl) &&
            Objects.equals(protocol, that.protocol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, requestUrl, protocol);
    }


}
