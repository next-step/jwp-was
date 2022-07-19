package model;

import types.HttpMethod;
import types.Protocol;

import java.util.Map;

public class RequestLine {
    private final HttpMethod httpMethod;
    private final String path;
    private final Map<String, String> queryParameters;
    private final Protocol protocol;
    private final String protocolVersion;

    public RequestLine(HttpMethod httpMethod, String path, Map<String, String> queryParameters, Protocol protocol, String protocolVersion) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.queryParameters = queryParameters;
        this.protocol = protocol;
        this.protocolVersion = protocolVersion;
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

    public String getProtocolVersion() {
        return protocolVersion;
    }

    @Override
    public String toString() {
        return "RequestLine{" +
                "httpMethod=" + httpMethod.name() +
                ", path='" + path + '\'' +
                ", queryParameters=" + queryParameters +
                ", protocol='" + protocol.name() + '\'' +
                ", protocolVersion='" + protocolVersion + '\'' +
                '}';
    }
}
