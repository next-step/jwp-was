package model;

import static utils.DelimiterConstants.QUERY_STRING_DELIMITER;

public class RequestLine {
    private HttpMethod method;
    private String path;
    private String parameters;
    private String protocol;
    private String version;

    private RequestLine(HttpMethod method, String path, String parameters, String protocol, String version) {
        this.method = method;
        this.path = path;
        this.parameters = parameters;
        this.protocol = protocol;
        this.version = version;
    }

    public static RequestLine createGetRequest(HttpMethod method, String path, String[] protocolAndVersion) {
        if (hasParameters(path)) {
            String[] splitPath = path.split(QUERY_STRING_DELIMITER);
            return new RequestLine(method, splitPath[0], splitPath[1], protocolAndVersion[0], protocolAndVersion[1]);
        }
        return new RequestLine(method, path, "", protocolAndVersion[0], protocolAndVersion[1]);
    }

    public static RequestLine createPostRequest(HttpMethod method, String path, String[] protocolAndVersion) {
        return new RequestLine(method, path, "", protocolAndVersion[0], protocolAndVersion[1]);
    }

    private static boolean hasParameters(String path) {
        return path.split(QUERY_STRING_DELIMITER).length >= 2;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getParameters() {
        return parameters;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }
}
