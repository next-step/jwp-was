package request;

import constant.HttpHeader;
import constant.HttpMethod;
import session.HttpSession;
import session.SessionManager;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    public static HttpRequest parse(InputStream in) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        List<String> lines = IOUtils.readLines(br);
        RequestHeader requestHeader = RequestHeader.from(lines.subList(1, lines.size()));

        return new HttpRequest(RequestLine.from(lines.get(0)),
                RequestHeader.from(lines.subList(1, lines.size())),
                RequestBody.parse(IOUtils.readData(br, requestHeader.getContentLength())));
    }

    public static HttpRequest from(List<String> lines) {
        return new HttpRequest(RequestLine.from(lines.get(0)),
                RequestHeader.from(lines.subList(1, lines.size())),
                RequestBody.empty());
    }

    public String getParameter(String key) {
        String value = requestLine.getParameter(key);
        if (value == null) {
            value = body.getParameter(key);
        }
        return value;
    }

    public String getHeader(String key) {
        return header.getHeader(key);
    }

    public HttpMethod getMethod() {
        return requestLine.getHttpMethod();
    }

    public String getBodyParameter(String key) {
        return body.getParameter(key);
    }

    public RequestHeader getHeader() {
        return header;
    }

    public RequestBody getBody() {
        return body;
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public RequestCookie getCoookie() {
        return header.getCookie();
    }

    public boolean isLogined() {
        return Objects.nonNull(getHttpSesion());
    }

    public boolean isGetRequest() {
        return requestLine.getHttpMethod().equals(HttpMethod.GET);
    }

    public HttpSession getHttpSesion() {
        return SessionManager.findBySessionId(getCoookie().getSessionId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpRequest request = (HttpRequest) o;
        return Objects.equals(requestLine, request.requestLine) && Objects.equals(header, request.header) && Objects.equals(body, request.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestLine, header, body);
    }
}
