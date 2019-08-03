package webserver.http;

import java.util.Map;

public class RequestLine {
    private static final int METHOD_INDEX = 0;
    private static final int METHOD_URL = 1;
    private static final int METHOD_PROTOCOL = 2;

    private HttpMethod method;
    private RequestURL requestURL;
    private String protocol;

    public RequestLine(HttpMethod method, RequestURL requestURL, String protocol) {
        this.method = method;
        this.requestURL = requestURL;
        this.protocol = protocol;
    }

    public static RequestLine parse(String requestLine) {
        String[] requestLineSplit = requestLine.split(" ");
        HttpMethod method = HttpMethod.find(requestLineSplit[METHOD_INDEX]);
        RequestURL requestURL = RequestURL.parse(requestLineSplit[METHOD_URL]);

        return new RequestLine(method, requestURL, requestLineSplit[METHOD_PROTOCOL]);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getQueryValue(String key) {
        return requestURL.getParameter(key);
    }

    public String getPath() {
        return requestURL.getPath();
    }

    public Map<String, String> getParameters() {
        return requestURL.getParameters();
    }
}
