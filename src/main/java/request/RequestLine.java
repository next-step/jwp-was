package request;

import constant.HttpMethod;

public class RequestLine {
    private static final String DELIMITER = " ";
    private static final int HTTP_METHOD_INDEX = 0;
    private static final int HTTP_PATH_INDEX = 1;
    private static final int HTTP_PROTOCOL_INDEX = 2;

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

    public String getPath() {
        return httpPath.getPath();
    }

    public String getParameter(String key) {
        return httpPath.getParameter(key);
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
