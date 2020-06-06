package http.request;

import http.RequestLine;
import http.RequestUrl;
import org.springframework.util.StringUtils;

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

    public void addRequestLine(RequestLine requestLine) {
        this.requestLine = requestLine;
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public boolean isPost() {
        return requestLine.isPost();
    }

    public RequestUrl findRequestUrl() {
        return RequestUrl.findByPath(requestLine.getPath());
    }

    public boolean isExistBody() {
        return StringUtils.hasText(body);
    }

    public String getBody() {
        return body;
    }

    public String getPath() {
        return requestLine.getPath();
    }
}
