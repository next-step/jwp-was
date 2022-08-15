package webserver;

import model.HttpMethod;

public class RequestLine {

    private static final int METHOD_INDEX = 0;
    private static final int PATH_INDEX = 1;
    private static final int PROTOCOL_AND_VERSION_INDEX = 2;
    private static final String REGEX_BLANK = " ";
    private static final String CREATE_PATH = "/user/create";
    private static final String EXTENSION_DELIMITER = "\\.";
    private static final int ONLY_PATH_INDEX = 0;

    private HttpMethod method;
    private RequestPath request;
    private Protocol protocol;

    public RequestLine(String requestLine) {
        this.method = parsingMethod(requestLine);
        this.request = parsingRequestPath(requestLine);
        this.protocol = parsingProtocol(requestLine);
    }

    private HttpMethod parsingMethod(String requestLine) {
        return HttpMethod.getType(
                requestLine.split(REGEX_BLANK)[METHOD_INDEX]);
    }

    private RequestPath parsingRequestPath(String requestLine) {
        return new RequestPath(requestLine.split(REGEX_BLANK)[PATH_INDEX]);
    }

    private Protocol parsingProtocol(String requestLine) {
        return new Protocol(requestLine.split(REGEX_BLANK)[PROTOCOL_AND_VERSION_INDEX]);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getFullRequestPath() {
        return request.getPath();
    }

    public String getRequestPath() {
        return request.getPath().split(EXTENSION_DELIMITER)[ONLY_PATH_INDEX];
    }

    public String getExtension() {
        final String[] split = request.getPath().split(EXTENSION_DELIMITER);
        return split[split.length - 1];
    }

    public String getRequestParams(String key) {
        return request.getParameter(key);
    }

    public String getProtocol() {
        return protocol.getProtocol();
    }

    public String getVersion() {
        return protocol.getVersion();
    }

    @Override
    public String toString() {
        return "RequestLine{" +
                "method=" + method +
                ", request=" + request +
                ", protocol=" + protocol +
                '}';
    }
}
