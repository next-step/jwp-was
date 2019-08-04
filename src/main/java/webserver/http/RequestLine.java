package webserver.http;

import java.util.Map;

public class RequestLine {

    private static final String REQUEST_LINE_SEPARATOR = " ";

    private String httpMethod;
    private RequestUri requestUri;
    private String httpVersion;

    private RequestLine(String httpMethod, RequestUri requestUri, String httpVersion) {
        this.httpMethod = httpMethod;
        this.requestUri = requestUri;
        this.httpVersion = httpVersion;
    }

    public static RequestLine parse(String line) {
        String[] values = line.split(REQUEST_LINE_SEPARATOR);

        RequestUri requestUri = RequestUriFactory.parse(values[1]);

        return new RequestLine(values[0], requestUri, values[2]);
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return requestUri.getPath();
    }

    public Map<String, String> getQueryParams() {
        return requestUri.getQueryParams();
    }

    public String getParameter(String key) {
        return requestUri.getParameter(key);
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    @Override
    public String toString() {
        return "RequestLine{" +
                "httpMethod='" + httpMethod + '\'' +
                ", requestUri=" + requestUri +
                ", httpVersion='" + httpVersion + '\'' +
                '}';
    }
}
