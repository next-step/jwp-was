package http.request;

import http.RequestLine;
import http.RequestUrl;

import java.util.Map;

public class Request {
    private RequestLine requestLine;
    private RequestHeader header;
    private String body;

    public Request(RequestLine requestLine, Map<String, String> header, String body) {
        this.requestLine = requestLine;
        this.header = new RequestHeader(header);
        this.body = body;
    }

    public RequestUrl findRequestUrl() {
        return RequestUrl.findByPath(requestLine.getPath());
    }

    public String getBody() {
        return body;
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public Map<String, String> getHeader() {
        return header.getHeader();
    }

    public boolean isLogin() {
        Map<String, String> cookies = header.getCookies();
        String cookie = cookies.getOrDefault("logined", "false");
        return Boolean.parseBoolean(cookie);
    }

    public boolean isStylesheet() {
        return StyleSheet.isContain(getPath());
    }
}
