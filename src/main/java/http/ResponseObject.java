package http;

public class ResponseObject {
    private final int code;
    private final String location;
    private final String requestPath;
    private final Object data;

    public ResponseObject(final int code, final String location, final String requestPath) {
        this(code, location, requestPath, null);
    }

    public ResponseObject(final int code, final String location, final String requestPath, final Object data) {
        this.code = code;
        this.location = location;
        this.requestPath = requestPath;
        this.data = data;
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

    public Object getData() {
        return data;
    }
}
