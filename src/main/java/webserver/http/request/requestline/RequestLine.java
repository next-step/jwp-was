package webserver.http.request.requestline;

public class RequestLine {
    private static final String BLANK_DELIMITER = " ";
    private static final int METHOD_INDEX = 0;
    private static final int PATH_INDEX = 1;
    private static final int PROTOCOL_AND_VERSION_INDEX = 2;
    private static final int REQUEST_PARSING_ELEMENT_NUMBER = 3;

    private Method method;
    private Path path;
    private Protocol protocol;

    RequestLine(Method method, Path path, Protocol protocol) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
    }

    public static RequestLine parse(String requestLineString) {
        validateRequestLineString(requestLineString);
        String[] elements = requestLineString.split(BLANK_DELIMITER);
        validateElementsLength(elements.length);

        Method method = Method.valueOf(elements[METHOD_INDEX]);
        Path path = Path.parse(elements[PATH_INDEX]);
        Protocol protocol = Protocol.parse(elements[PROTOCOL_AND_VERSION_INDEX]);

        return new RequestLine(method, path, protocol);
    }

    private static void validateRequestLineString(String requestLine) {
        if (requestLine == null || requestLine.isEmpty()) {
            throw new IllegalArgumentException("요청된 HTTP RequestLine 은 null 이거나 비어있을 수 없습니다.");
        }
    }

    private static void validateElementsLength(int length) {
        if (length != REQUEST_PARSING_ELEMENT_NUMBER) {
            throw new IllegalArgumentException(String.format("요청된 HTTP RequestLine 을 공백으로 파싱한 정보 갯수가 [method] + [path] + [protocol/version]로 총 3이여야 합니다. 현재 파싱된 정보 갯수 : %d", length));
        }
    }

    public String getPath() {
        return this.path.getPath();
    }

    public String getQueryStringValue(String key) {
        return this.path.getQueryStringValue(key);
    }

    public boolean isFilePath() {
        return this.path.isFilePath();
    }

    public boolean isMethodEqual(Method method) {
        return this.method.isMethodEqual(method);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestLine that = (RequestLine) o;

        if (method != that.method) return false;
        if (!path.equals(that.path)) return false;
        return protocol.equals(that.protocol);
    }

    @Override
    public int hashCode() {
        int result = method.hashCode();
        result = 31 * result + path.hashCode();
        result = 31 * result + protocol.hashCode();
        return result;
    }
}
