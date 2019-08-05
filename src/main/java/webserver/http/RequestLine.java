package webserver.http;

public class RequestLine {
    private static final String REQUEST_LINE_SEPARATOR = " ";
    private static final int METHOD_INDEX = 0;
    private static final int URI_INDEX = 1;
    private static final int VERSION_INDEX = 2;

    private HttpMethod method;
    private URI uri;
    private String version;

    private RequestLine(String method, String uriString, String version) {
        this.method = HttpMethod.valueOf(method);
        this.uri = URI.parse(uriString);
        this.version = version;
    }

    public static RequestLine parse(String path) {
        String[] values = path.split(REQUEST_LINE_SEPARATOR);
        return new RequestLine(values[METHOD_INDEX], values[URI_INDEX], values[VERSION_INDEX]);
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    public URI getUri() {
        return this.uri;
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
