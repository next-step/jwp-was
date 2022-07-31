package request;

import constant.HttpHeader;
import constant.HttpMethod;
import java.io.BufferedReader;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class HttpRequest {

    private final RequestLine requestLine;
    private final RequestHeader header;
    private final RequestBody body;

    public HttpRequest(RequestLine requestLine, RequestHeader header, RequestBody body) {
        this.requestLine = requestLine;
        this.header = header;
        this.body = body;
    }

    public static HttpRequest from(List<String> lines) {
        return new HttpRequest(RequestLine.from(lines.get(0)),
            RequestHeader.from(lines.subList(1, lines.size())),
            RequestBody.empty());
    }

    public String getParameter(String key) {
        return requestLine.getParameter(key);
    }

    public String getHeader(String key) {
        return header.getHeader(key);
    }

    public void addBody(String body) {
        if(!body.equals("")) {
            this.body.addBody(body);
        }
    }

    public String getBodyParameter(String key) {
        return body.getPameter(key);
    }

    public int getContentLength() {
        if(header.getHeader(HttpHeader.CONTENT_LENGTH.getValue()) == null) {
            return 0;
        }
        return Integer.parseInt(header.getHeader(HttpHeader.CONTENT_LENGTH.getValue()));
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public RequestHeader getHeader() {
        return header;
    }

    public RequestBody getBody() {
        return body;
    }

    public HttpMethod getHttpMethod() {
        return requestLine.getHttpMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public Cookie getCoookie() {
        return header.getCookie();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HttpRequest that = (HttpRequest) o;
        return Objects.equals(requestLine, that.requestLine) && Objects.equals(
            header, that.header) && Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestLine, header, body);
    }
}
