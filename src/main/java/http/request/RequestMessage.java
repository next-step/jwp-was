package http.request;


import http.common.HttpSession;
import http.common.HttpSessionStorage;
import http.common.HttpCookie;
import http.common.HttpHeader;
import http.common.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import utils.StringUtils;


import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class RequestMessage {
    private static final Logger logger = getLogger(RequestMessage.class);

    public static final String REQUEST_MESSAGE_IS_INVALID = "request message is invalid.";

    private final RequestLine requestLine;
    private final HttpHeaders httpHeaders;
    private final String body;

    private RequestMessage(RequestLine requestLine, HttpHeaders httpHeaders, String body) {
        if (requestLine == null || httpHeaders == null || body == null) {
            throw new IllegalArgumentException(REQUEST_MESSAGE_IS_INVALID);
        }
        this.requestLine = requestLine;
        this.httpHeaders = httpHeaders;
        this.body = body;
    }

    public static RequestMessage from(BufferedReader br) throws IOException {
        RequestLine requestLine = readRequestLine(br);
        HttpHeaders httpHeaders = readHeader(br);
        String body = readBody(br, requestLine, httpHeaders);
        return new RequestMessage(requestLine, httpHeaders, body);
    }

    private static RequestLine readRequestLine(BufferedReader br) throws IOException {
        String line = br.readLine();
        if (line == null) {
            throw new EOFException();
        }
        return RequestLine.from(line);
    }

    private static HttpHeaders readHeader(BufferedReader br) throws IOException {
        String line;
        List<String> others = new ArrayList<>();
        while (!StringUtils.isEmpty(line = br.readLine())) {
            others.add(line);
        }
        return new HttpHeaders(others);
    }

    private static String readBody(BufferedReader br, RequestLine requestLine, HttpHeaders httpHeaders) throws IOException {
        String body = "";
        if (requestLine.hasBody()) {
            int contentLength = Integer.parseInt(httpHeaders.getHeaderValueBy(HttpHeader.CONTENT_LENGTH));
            body = IOUtils.readData(br, contentLength);
        }
        return body;
    }

    public static RequestMessage createWithDefaultBody(RequestLine requestLine, HttpHeaders httpHeaders) {
        return new RequestMessage(requestLine, httpHeaders, "");
    }

    public static RequestMessage create(RequestLine requestLine, HttpHeaders httpHeaders, String body) {
        return new RequestMessage(requestLine, httpHeaders, body);
    }

    public String getPath() {
        return this.requestLine.getPath();
    }

    public QueryString getQueryString() {
        return this.requestLine.getQueryString();
    }

    public QueryString readBody() {
        return new QueryString(body);
    }

    public HttpMethod getMethod() {
        return this.requestLine.getMethod();
    }

    public Uri getUri() {
        return this.requestLine.getUri();
    }

    public HttpCookie getCookie() {
        return HttpCookie.from(this.httpHeaders.getHeaderValueBy(HttpHeader.COOKIE));
    }

    public HttpSession getSession() {
        HttpCookie cookie = getCookie();
        return HttpSessionStorage.getOrCreate(cookie.getCookieValueBy(HttpCookie.SESSION_ID));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestMessage that = (RequestMessage) o;
        return Objects.equals(requestLine, that.requestLine) &&
                Objects.equals(httpHeaders, that.httpHeaders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestLine, httpHeaders);
    }
}
