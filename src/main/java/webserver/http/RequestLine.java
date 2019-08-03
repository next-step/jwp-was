package webserver.http;

public class RequestLine {
    private final String version;
    private final String method;
    private final RequestURL requestURL;

    public RequestLine(String method, RequestURL requestURL, String version) {
        this.method = method;
        this.requestURL = requestURL;
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return requestURL.getPath();
    }

    public String getParameters(String key) {
        return requestURL.getQuery(key);
    }

    public static RequestLine parse(String requestLine) {
        String[] splitedRequestLine = requestLine.split(" ");
        String method = splitedRequestLine[0];
        String path = splitedRequestLine[1];
        String version = splitedRequestLine[2];
        return new RequestLine(method, RequestURL.parse(path), version);
    }

}
