package webserver;

public class RequestLine {


    private static final int NUMBER_OF_REQUEST_LINE_PROPERTIES = 3;
    private static final int METHOD_IDX = 0;
    private static final int PATH_IDX = 1;
    private static final int PROTOCOL_IDX = 2;
    private static final String REQUEST_LINE_SPACE = " ";

    private String method;
    private String path;
    private String protocol;

    public RequestLine(String method, String path, String protocol) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
    }

    public static RequestLine parse(String requestLine) {
        String[] splits = requestLine.split(REQUEST_LINE_SPACE);
        if (splits.length != NUMBER_OF_REQUEST_LINE_PROPERTIES) {
            throw new IllegalArgumentException("올바른 Request Line이 아닙니다");
        }

        return new RequestLine(
                splits[METHOD_IDX],
                splits[PATH_IDX],
                splits[PROTOCOL_IDX]
        );
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol;
    }
}
