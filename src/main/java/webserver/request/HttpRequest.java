package webserver.request;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpMethod;
import webserver.Cookie;
import webserver.Header;
import webserver.session.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private RequestLine requestLine;
    private Header header;
    private RequestBody requestBody;
    private Cookie cookie;
    private HttpSession session;

    public HttpRequest(RequestLine requestLine, Header header, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.header = header;
        this.requestBody = requestBody;
        this.cookie = header.parseCookie();
        this.session = new HttpSession();
    }

    public static HttpRequest parsing(BufferedReader br) throws IOException {
        RequestLine requestLine = RequestLine.parsing(br);
        Header header = Header.parsing(br);
        RequestBody requestBody = new RequestBody();
        if (requestLine.getMethod() == HttpMethod.POST) {
            requestBody = RequestBody.parsing(br, header.getContentLength());
        }

        return new HttpRequest(requestLine, header, requestBody);
    }

    public User convertUserOfQueryParam() {
        if (isPostMethod()) {
            return requestBody.bodyToUser();
        }

        return requestLine.queryStringToUser();
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

    public String getCookie(String key) {
        return cookie.getCookie(key);
    }

    public String getHeader(String key) {
        return header.get(key);
    }

    public String getParameter(String key) {
        return requestBody.getBody().get(key);
    }

    public HttpSession getSession() {
        return session;
    }
}
