package webserver;

public class RequestLine {

    private static final int REQUEST_LINE_LENGTH = 3;

    private static final String REQUEST_LINE_DELIMITER = " ";

    private static final int METHOD_IDX = 0;

    private static final int PATH_IDX = 1;

    private static final int PROTOCOL_IDX = 2;

    private final HttpMethod method;

    private final String path;

    private final HttpProtocol protocol;

    private RequestLine(HttpMethod method, String path, HttpProtocol protocol) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
    }

    public static RequestLine parseOf(String httpRequestLine) {
        String[] requestLineValues = httpRequestLine.split(REQUEST_LINE_DELIMITER);

        if (requestLineValues.length != REQUEST_LINE_LENGTH) {
            throw new IllegalArgumentException(String.format("RequestLine 파싱 실패 [%s]", httpRequestLine));
        }

        return new RequestLine(
                HttpMethod.valueOf(requestLineValues[METHOD_IDX]),
                requestLineValues[PATH_IDX],
                HttpProtocol.parseOf(requestLineValues[PROTOCOL_IDX]));
    }

    HttpMethod getMethod() {
        return method;
    }

    HttpProtocol getProtocol() {
        return protocol;
    }

    String getPath() {
        return path;
    }
}
