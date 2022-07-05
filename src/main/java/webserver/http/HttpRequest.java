package webserver.http;

public class HttpRequest {

    private final HttpMethod httpMethod;

    private final String path;

    private final String protocol;

    private final String version;

    public HttpRequest(String httpMethod, String path, String protocol, String version) {
        this.httpMethod = HttpMethod.valueOf(httpMethod);
        this.path = path;
        this.protocol = protocol;
        this.version = version;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }
}
