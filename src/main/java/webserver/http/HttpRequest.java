package webserver.http;

public class HttpRequest {

    private final HttpMethod httpMethod;

    private final HttpProtocol httpProtocol;

    private final URI uri;

    public HttpRequest(String httpMethod, String path, String protocol) {
        this.httpMethod = HttpMethod.valueOf(httpMethod);
        this.httpProtocol = new HttpProtocol(protocol);
        this.uri = new URI(path);
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public HttpProtocol getHttpProtocol() {
        return httpProtocol;
    }

    public URI getUri() {
        return uri;
    }
}
