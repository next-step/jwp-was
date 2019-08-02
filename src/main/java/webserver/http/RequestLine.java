package webserver.http;

import java.util.Map;

public class RequestLine {

    private static final String REQUEST_LINE_SEPARATOR = " ";

    private String httpMethod;
    private String path;
    private Map<String, String> queryParams;
    private String httpVersion;

    private RequestLine(String httpMethod, String path, Map<String, String> queryParams, String httpVersion) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.queryParams = queryParams;
        this.httpVersion = httpVersion;
    }

    public static RequestLine parse(String line) {
        String[] values = line.split(REQUEST_LINE_SEPARATOR);

        RequestUriFactory requestUri = RequestUriFactory.parse(values[1]);

        return new RequestLine(values[0], requestUri.getPath(), requestUri.getQueryParams(), values[2]);
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public String getHttpVersion() {
        return httpVersion;
    }
}
