package model;

import types.HttpMethod;
import types.Protocol;

import java.util.Objects;

public class RequestLine {
    private final HttpMethod httpMethod;
    private final UrlPath urlPath;
    private final Protocol protocol;

    public RequestLine(HttpMethod httpMethod, UrlPath urlPath, Protocol protocol) {
        this.httpMethod = httpMethod;
        this.urlPath = urlPath;
        this.protocol = protocol;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public UrlPath getUrlPath() {
        return urlPath;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return httpMethod == that.httpMethod && urlPath.equals(that.urlPath) && protocol == that.protocol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpMethod, urlPath, protocol);
    }

    public String getInfo() {
        String value = String.format("HttpMethod : %s \n", this.httpMethod) +
                String.format("urlPath : %s \n", this.urlPath.getInfo()) +
                String.format("Protocol : %s \n", this.protocol.getInfo());
        return value;
    }

}
