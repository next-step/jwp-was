package webserver;

public class RequestLine {

    private String method;
    private String path;
    private String protocol;
    private String version;

    protected RequestLine() {

    }

    private RequestLine(String method, String path, String protocol, String version) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
        this.version = version;
    }

    public RequestLine parse(String request) {
        if (request == null) {
            throw new IllegalArgumentException("요청 내용이 NULL입니다.");
        }

        String[] parsingRequest = request.split(" ");
        String[] parsingProtocolVersion = parsingRequest[2].split("/");

        return new RequestLine(parsingRequest[0], parsingRequest[1], parsingProtocolVersion[0],
            parsingProtocolVersion[1]);
    }

    public String getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public String getVersion() {
        return this.version;
    }
}
