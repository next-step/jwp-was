package webserver.http;

public class RequestLine {

    private static final String REQUEST_DELIMITER =" ";

    private String method;
    private RequestPath path;
    private String version;

    private RequestLine(String method, RequestPath path, String version) {
        this.method = method;
        this.path = path;
        this.version = version;
    }

    public static RequestLine parse(String requestLineValue) {
        String[] values = requestLineValue.split(REQUEST_DELIMITER);

        return new RequestLine(values[0], RequestPath.parse(values[1]), values[2]);
    }

    public String getMethod() {
        return method;
    }

    public RequestPath getPath() {
        return path;
    }

    public String getVersion() {
        return version;
    }
}
