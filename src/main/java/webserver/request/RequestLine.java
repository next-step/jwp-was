package webserver.request;

import enums.HttpMethod;
import java.util.Map;
import java.util.regex.Pattern;

public class RequestLine {
    private static final Pattern PATTERN = Pattern.compile("(^(GET|POST) /*.* (HTTP/(0.9|1.0|1.1)))");

    private final HttpMethod method;
    private final RequestURI uri;
    private final Protocol protocol;

    public RequestLine(HttpMethod method, RequestURI uri, Protocol protocol) {
        this.method = method;
        this.uri = uri;
        this.protocol = protocol;
    }

    public RequestLine(String httpRequest) {
        String[] splitRequestLine = httpRequest.split(" ");

        this.method = HttpMethod.valueOf(splitRequestLine[0]);
        this.uri = new RequestURI(splitRequestLine[1]);
        this.protocol = new Protocol(splitRequestLine[2]);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getProtocol() {
        return protocol.getName();
    }

    public String getVersion() {
        return protocol.getVersion();
    }

    public String getPath() {
        return uri.getPath();
    }

    public QueryString getQueryString() {
        return uri.getQueryString();
    }
}
