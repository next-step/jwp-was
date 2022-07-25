package webserver.request;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.HttpMethod;
import webserver.Header;

import java.io.BufferedReader;
import java.io.IOException;

public class Request {

    private static final Logger logger = LoggerFactory.getLogger(Request.class);
    private RequestLine requestLine;
    private Header header;
    private RequestBody requestBody;
    private Cookie cookie;

    public Request(RequestLine requestLine, Header header, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.header = header;
        this.requestBody = requestBody;
        this.cookie = header.parseCookie();
    }

    public static Request parsing(BufferedReader br) throws IOException {
        RequestLine requestLine = RequestLine.getInstance().parsing(br);
        Header header = Header.parsing(br);
        RequestBody requestBody = RequestBody.getInstance().parsing(br, header.getContentLength());

        return new Request(requestLine, header, requestBody);
    }

    public User convertUserOfQueryParam() {
        if (isPostMethod()) {
            return requestBody.bodyToUser();
        }

        return requestLine.queryStringToUser();
    }

    public boolean requestPathCheck(String path) {
        return getRequestUri().startsWith(path);
    }

    public boolean requestPathCheckAndEndWithHtml(String path) {
        return getRequestUri().startsWith(path) && !FileIoUtils.isLastEndWithHtml(getRequestUri());
    }

    public boolean isPostMethod() {
        return HttpMethod.POST.equals(getMethod());
    }

    public String getRequestUri() {
        return requestLine.getUri();
    }

    public String getRequestPath() {
        return requestLine.getPathExcludeQueryParam();
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public boolean getCookie(String isLogined) {
        return Boolean.parseBoolean(cookie.getCookie(isLogined));
    }
}
