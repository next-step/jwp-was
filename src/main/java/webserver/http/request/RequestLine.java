package webserver.http.request;

public class RequestLine {

    private static final String REQUEST_DELIMITER =" ";

    private HttpMethod method;
    private RequestUri uri;
    private String version;

    private RequestLine(HttpMethod method, RequestUri uri, String version) {
        this.method = method;
        this.uri = uri;
        this.version = version;
    }

    public static RequestLine parse(String requestLineValue) {
        String[] values = requestLineValue.split(REQUEST_DELIMITER);

        return new RequestLine(HttpMethod.find(values[0]), RequestUri.parse(values[1]), values[2]);
    }

    public boolean isPost() {
        return method == HttpMethod.POST;
    }

    public boolean isGet() {
        return method == HttpMethod.GET;
    }

    public String getRequestUriPath() {
        return uri.getPath();
    }

    public String getParameter(String key) {
        return uri.getParameter(key);
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
