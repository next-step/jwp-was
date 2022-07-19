package webserver;

import java.util.Arrays;
import java.util.Objects;

public class RequestLine {
    private static final int VALID_NUMBER_OF_PROPERTIES = 3;

    private String method;
    private String path;
    private String protocol;

    private RequestLine(String method, String path, String protocol) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
    }

    public static RequestLine from(String httpRequest) {
        String[] properties = httpRequest.split(" ");
        validate(properties);
        return new RequestLine(properties[0], properties[1], properties[2]);
    }

    private static void validate(String[] parsedLine) {
        if (parsedLine.length != VALID_NUMBER_OF_PROPERTIES) {
            throw new IllegalArgumentException(String.format("필요한 속성의 개수[%d]를 만족하지 않습니다.", 3));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return Objects.equals(method, that.method) && Objects.equals(path, that.path) && Objects.equals(protocol, that.protocol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, path, protocol);
    }

    @Override
    public String toString() {
        return "RequestLine{" +
                "method='" + method + '\'' +
                ", path='" + path + '\'' +
                ", protocol='" + protocol + '\'' +
                '}';
    }
}
