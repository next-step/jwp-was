package model;

import types.HttpMethod;
import types.Protocol;

import java.util.Map;

public class RequestLine {
    private final HttpMethod httpMethod;
    private final String path;
    private final Map<String, String> queryParameters;
    private final Protocol protocol;

    public RequestLine(HttpMethod httpMethod, String path, Map<String, String> queryParameters, Protocol protocol) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.queryParameters = queryParameters;
        this.protocol = protocol;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getQueryParameters() {
        return queryParameters;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    @Override
    public String toString() {
        return "RequestLine{" +
                "httpMethod=" + httpMethod +
                ", path='" + path + '\'' +
                ", queryParameters=" + queryParameters +
                ", protocol=" + protocol.name() + "/" + protocol.getVersion() +
                '}';
    }
}
