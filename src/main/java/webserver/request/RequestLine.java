package webserver.request;

import enums.HttpMethod;
import java.util.regex.Pattern;

public class RequestLine {
    private static final Pattern PATTERN = Pattern.compile("(^(GET|POST) /*.* (HTTP/(0.9|1.0|1.1)))");

    private final HttpMethod method;
    private final RequestURI uri;
    private final Protocol protocol;

    public RequestLine(String httpRequest) {
        if (!PATTERN.matcher(httpRequest).matches()) throw new IllegalArgumentException();

        String[] values = httpRequest.split(" ");

        this.method = HttpMethod.valueOf(values[0]);
        this.uri = new RequestURI(values[1]);
        this.protocol = new Protocol(values[2]);
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

    public String getQueryString() {
        return uri.getQueryString();
    }
}
