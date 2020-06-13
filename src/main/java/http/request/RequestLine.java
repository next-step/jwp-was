package http.request;

import http.common.ProtocolAndVersion;

import java.util.Objects;

public class RequestLine {
    private final HttpMethod method;
    private final String path;
    private final Parameters queryString;
    private final ProtocolAndVersion protocolAndVersion;

    public RequestLine(HttpMethod method, String path, Parameters queryString, ProtocolAndVersion protocolAndVersion) {
        this.method = method;
        this.path = path;
        this.queryString = queryString;
        this.protocolAndVersion = protocolAndVersion;
    }

    public String getPath() {
        return path;
    }

    public String getParameter(String parameterName) {
        return queryString.getValue(parameterName);
    }

    public HttpMethod getHttpMethod() {
        return method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return Objects.equals(method, that.method) &&
                Objects.equals(path, that.path) &&
                Objects.equals(protocolAndVersion, that.protocolAndVersion) &&
                Objects.equals(queryString, that.queryString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, path, protocolAndVersion, queryString);
    }

}
