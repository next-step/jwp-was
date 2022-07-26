package webserver;

import model.HttpMethod;

import java.util.List;

public class RequestLine {

    public static final int METHOD_INDEX = 0;
    public static final int PATH_INDEX = 1;
    public static final int PROTOCOL_AND_VERSION_INDEX = 2;
    public static final String REGEX_BLANK = " ";

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

    public RequestParams getRequestParams() {
        return request.getParams();
    }

    public String getProtocol() {
        return protocol.getProtocol();
    }

    public String getVersion() {
        return protocol.getVersion();
    }

    public boolean isCreateUserPath() {
        return request.getPath().equals("/create");
    }
}
