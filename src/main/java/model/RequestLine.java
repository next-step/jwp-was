package model;

import constant.HttpMethod;

import java.util.Map;

public class RequestLine {
    public static final String DELIMITER = " ";
    public static final int HTTP_METHOD_INDEX = 0;
    public static final int HTTP_PATH_INDEX = 1;
    public static final int HTTP_PROTOCOL_INDEX = 2;

    private HttpMethod httpMethod;
    private HttpPath httpPath;
    private HttpProtocol httpProtocol;

    public RequestLine(HttpMethod httpMethod, HttpPath httpPath, HttpProtocol httpProtocol) {
        this.httpMethod = httpMethod;
        this.httpPath = httpPath;
        this.httpProtocol = httpProtocol;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public HttpPath getHttpPath() {
        return httpPath;
    }

    public HttpProtocol getHttpProtocol() {
        return httpProtocol;
    }

    public static RequestLine from(String line) {
        String[] attributes = line.split(DELIMITER);

        return new RequestLine(
                HttpMethod.of(attributes[HTTP_METHOD_INDEX]),
                HttpPath.Instance(attributes[HTTP_PATH_INDEX]),
                HttpProtocol.Instance(attributes[HTTP_PROTOCOL_INDEX])
        );
    }

    @Override
    public String toString() {
        return "RequestLine{" +
            "httpMethod=" + httpMethod +
            ", httpPath=" + httpPath +
            ", httpProtocol=" + httpProtocol +
            '}';
    }
}
