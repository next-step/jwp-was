package http;

/**
 * Created by iltaek on 2020/06/03 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class RequestLine {

    private final HttpMethod httpMethod;
    private final RequestURI requestURI;
    private final ProtocolVersion protocolVersion;

    public RequestLine(String httpMethod, String path, ProtocolVersion protocolVersion) {
        this.httpMethod = HttpMethod.of(httpMethod);
        this.requestURI = new RequestURI(path);
        this.protocolVersion = protocolVersion;
    }

    public String getHttpMethod() {
        return this.httpMethod.toString();
    }

    public String getPath() {
        return this.requestURI.getPath();
    }

    public String getQueryString() {
        return this.requestURI.getQueryString();
    }

    public String getProtocolVersion() {
        return this.protocolVersion.getHttpProtocol();
    }

    public String getVersion() {
        return this.protocolVersion.getVersion();
    }
}
