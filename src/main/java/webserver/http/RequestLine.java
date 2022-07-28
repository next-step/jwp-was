package webserver.http;

import java.util.Objects;

public class RequestLine {
    public static final String VALIDATION_MESSAGE = "Request Line이 형식에 맞지 않습니다.";
    private static final String REQUEST_DELIMITER = " ";
    private static final int CORRECT_LENGTH = 3;

    private final HttpMethod httpMethod;
    private final HttpPath httpPath;
    private final HttpProtocol httpProtocol;

    public RequestLine(String requestLine) {
        final String[] requestSpecs = requestLine.split(REQUEST_DELIMITER);
        validate(requestSpecs);

        this.httpMethod = HttpMethod.valueOf(requestSpecs[0]);
        this.httpPath = new HttpPath(requestSpecs[1]);
        this.httpProtocol = new HttpProtocol(requestSpecs[2]);
    }

    public RequestLine(HttpMethod httpMethod, HttpPath httpPath, HttpProtocol httpProtocol) {
        this.httpMethod = httpMethod;
        this.httpPath = httpPath;
        this.httpProtocol = httpProtocol;
    }

    private void validate(String[] requestSpecs) {
        if (requestSpecs.length != CORRECT_LENGTH) {
            throw new IllegalArgumentException(VALIDATION_MESSAGE);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return Objects.equals(httpMethod, that.httpMethod) && Objects.equals(httpPath, that.httpPath) && Objects.equals(httpProtocol, that.httpProtocol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpMethod, httpPath, httpProtocol);
    }

    @Override
    public String toString() {
        return "RequestLine{" +
                "httpMethod=" + httpMethod +
                ", httpPath=" + httpPath +
                ", httpProtocol=" + httpProtocol +
                '}';
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return httpPath.getPath();
    }

    boolean isGET() {
        return getHttpMethod() == HttpMethod.GET;
    }
}
