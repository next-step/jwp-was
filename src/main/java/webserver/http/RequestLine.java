package webserver.http;

public class RequestLine {

    private static final String REQUEST_DELIMITER =" ";

    private String method;
    private RequestUri uri;
    private String version;

    private RequestLine(String method, RequestUri uri, String version) {
        this.method = method;
        this.uri = uri;
        this.version = version;
    }

    public static RequestLine parse(String requestLineValue) {
        String[] values = requestLineValue.split(REQUEST_DELIMITER);

        return new RequestLine(values[0], RequestUri.parse(values[1]), values[2]);
    }

    public String getMethod() {
        return method;
    }

    public RequestUri getUri() {
        return uri;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "RequestLine{" +
                "method='" + method + '\'' +
                ", uri=" + uri +
                ", version='" + version + '\'' +
                '}';
    }
}
