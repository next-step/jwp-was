package utils;

import exception.NotExistHttpMethodException;

public class RequestLine {

    private static final String NOT_EXIST_HTTP_METHOD = "존재하지 않는 HTTP METHOD 입니다.";
    private String path;
    private HttpMethod method;
    private String protocol;
    private String version;

    private RequestLine() {
    }

    private static class InnerInstanceRequestLine {
        private static final RequestLine instance = new RequestLine();
    }

    public static RequestLine getInstance() {
        return InnerInstanceRequestLine.instance;
    }

    public RequestLine parsing(String firstLine) {
        String[] requestDataArray = firstLine.split(" ");
        String method = requestDataArray[0];
        String path = requestDataArray[1];
        if (!HttpMethod.checkHttpMethod(method)) {
            throw new NotExistHttpMethodException(NOT_EXIST_HTTP_METHOD);
        }

        String[] protocolAndVersion = requestDataArray[2].split("/");
        return setting(HttpMethod.valueOf(method), path, protocolAndVersion[0], protocolAndVersion[1]);
    }

    public String getQueryParam(String requestLine) {
        String[] requestLineArr = requestLine.split(" ");
        String path = requestLineArr[1];
        if (path.contains("?")) {
            int firstIndex = path.indexOf("?") + 1;
            return path.substring(firstIndex);
        }
        return "";
    }

    private RequestLine setting(HttpMethod method, String path, String protocol, String version) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
        this.version = version;
        return this;
    }

    public String getPath() {
        return path;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }
}
