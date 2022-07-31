package webserver.domain;

import org.springframework.http.HttpMethod;

/**
 * 요청 라인
 */
public class RequestLine {
    public static final String QUERY_DELIMITER = "\\?";
    public static final String DELIMITER = " ";
    public static final int PATH_POINT = 1;
    public static final int PROTOCOL_AND_VERSION_POINT = 2;
    public static final int METHOD_POINT = 0;
    public static final String INVALID_REQUEST_LINE_MSG = "유효하지 않은 RequestLine입니다. valeu:";

    private HttpMethod method;
    private Protocol protocol;
    private Path path;

    public RequestLine() {
    }

    public RequestLine(HttpMethod method, Path path, Protocol protocol) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
    }


    /**
     * 요청 라인 문자열에서 method, queryString, protocol을 추출해 요청 정보 객체를 생성해 반환한다.
     *
     * @param line 요청 라인 문자열
     * @return 요청 정보
     */
    public static RequestLine from(String line) {
        if (line == null || line.isEmpty()) {
            throw new IllegalArgumentException(INVALID_REQUEST_LINE_MSG + line);
        }

        String[] attributes = line.split(DELIMITER);

        HttpMethod httpMethod = HttpMethod.valueOf(attributes[METHOD_POINT]);
        Path path = new Path(attributes[PATH_POINT].split(QUERY_DELIMITER)[0]);
        Protocol protocol = Protocol.newInstance(attributes[PROTOCOL_AND_VERSION_POINT]);

        return new RequestLine(httpMethod, path, protocol);
    }


    /**
     * 요청 메서드를 반환한다.
     */
    public HttpMethod getMethod() {
        return method;
    }

    /**
     * 요청 경로 객체를 반환한다.
     */
    public Path getPath() {
        return path;
    }

    /**
     * 요청 프로토콜을 반환한다.
     */
    public Protocol getProtocol() {
        return protocol;
    }

    /**
     * 요청 경로 문자열을 반환한다.
     */
    public String getPathStr() {
        return path.getPathStr();
    }

    @Override
    public String toString() {
        return method.name() + " " + path + " " + protocol;
    }
}
