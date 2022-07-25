package webserver.http;

public class RequestLine {

    private static final String MESSAGE_EMPTY_SPACE = " ";

    private HttpMethod method;
    private RequestPath path;
    private HttpProtocolVersion protocolVersion;

    public RequestLine(String requestMessage) {
        String[] splitRequestMessage = requestMessage.split(MESSAGE_EMPTY_SPACE);
        if (splitRequestMessage.length != 3) {
            throw new IllegalArgumentException("잘못된 HTTP 요청 메시지입니다.");
        }
        this.method = HttpMethod.of(splitRequestMessage[0]);
        this.path = new RequestPath(splitRequestMessage[1]);
        this.protocolVersion = new HttpProtocolVersion(splitRequestMessage[2]);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path.getPath();
    }

    public String getProtocol() {
        return protocolVersion.getProtocol();
    }

    public String getVersion() {
        return protocolVersion.getVersion();
    }

    public String getQueryString() {
        return path.getQueryString();
    }
}
