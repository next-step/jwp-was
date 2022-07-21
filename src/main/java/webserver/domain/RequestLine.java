package webserver.domain;

public class RequestLine {
    private static final String SPACE_DELIMITER = " ";
    private static final int REQUEST_LINE_LENGTH = 3;

    private final HttpMethod httpMethod;
    private final UrlInfo urlInfo;
    private final HttpProtocol httpProtocol;

    public RequestLine(String requestLine) {
        String[] s = requestLine.split(SPACE_DELIMITER);
        validate(s);
        this.httpMethod = HttpMethod.of(s[0]);
        this.urlInfo = new UrlInfo(s[1]);
        this.httpProtocol = new HttpProtocol(s[2]);
    }

    private void validate(String[] s) {
        if (s.length != REQUEST_LINE_LENGTH) {
            throw new IllegalArgumentException("RequestLine 형식이 아닙니다.");
        }
    }

    public HttpMethod getMethod() {
        return httpMethod;
    }

    public String getPath() {
        return urlInfo.getPath();
    }

    public Parameters getParameters() {
        return urlInfo.getParameters();
    }

    public String getProtocol() {
        return httpProtocol.getProtocol();
    }

    public String getProtocolVersion() {
        return httpProtocol.getVersion();
    }

    public String getParameter(String key) {
        return urlInfo.getParameters().getParameter(key);
    }
}
