package http;

public class Response {
    private final String protocol;
    private final int code;
    private final String status;
    private final String contentType;
    private final String cookie;
    private final boolean logined;
    private final String path;

    public Response(final String protocol, final int code, final String status, final String contentType, final String cookie, final boolean logined, final String path) {
        this.protocol = protocol;
        this.code = code;
        this.status = status;
        this.contentType = contentType;
        this.cookie = cookie;
        this.logined = logined;
        this.path = path;
    }

    public String getProtocol() {
        return protocol;
    }

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public String getContentType() {
        return contentType;
    }

    public String getCookie() {
        return cookie;
    }

    public boolean isLogined() {
        return logined;
    }

    public String getPath() {
        return path;
    }
}
