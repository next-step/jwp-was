package http;

public class ResponseObject {
    private final int code;
    private final String location;
    private final String requestPath;

    public ResponseObject(final int code, final String location, final String requestPath) {
        this.code = code;
        this.location = location;
        this.requestPath = requestPath;
    }

    public int getCode() {
        return code;
    }

    public String getLocation() {
        return location;
    }

    public String getRequestPath() {
        return requestPath;
    }
}
