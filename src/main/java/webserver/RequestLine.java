package webserver;

import model.HttpMethod;

public class RequestLine {

    public static final int METHOD_INDEX = 0;
    public static final int PATH_INDEX = 1;
    public static final int PROTOCOL_AND_VERSION_INDEX = 2;
    public static final String REGEX_BLANK = " ";
    public static final String CREATE_PATH = "/user/create";
    public static final String EXTENSION_DELIMITER = "\\.";
    public static final int EXTENTION_INDEX = 1;

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

    public String getRequestPath() {
        return request.getPath();
    }

    public String getExtension() {
        final String[] split = request.getPath().split(EXTENSION_DELIMITER);
        return split[split.length - 1];
    }

    public RequestParams getRequestParams() {
        return request.getParams();
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
