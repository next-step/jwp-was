package webserver.http;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class HttpRequest {

    private final RequestLine requestLine;

    private final Headers headers;

    private final RequestBody body;

    private final List<Cookie> cookies;

    private HttpSession httpSession;

    public HttpRequest(RequestLine requestLine, Headers headers, RequestBody body) {
        this.requestLine = requireNonNull(requestLine, "");
        this.headers = requireNonNull(headers, "");
        this.body = body;
        this.cookies = Cookie.listOf(this.headers.getValue("cookie"));
    }

    public HttpRequest(RequestLine requestLine, Headers headers, String body) {
        this(requestLine, headers, new RequestBody(body));
    }

    public HttpRequest(RequestLine requestLine, Headers headers) {
        this(requestLine, headers, RequestBody.EMPTY);
    }

    public HttpRequest(RequestLine requestLine) {
        this(requestLine, new Headers());
    }

    public HttpRequest(String requestLine) {
        this(RequestLine.parseOf(requestLine));
    }

    public static HttpRequest create(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        RequestLine requestLine = RequestLine.parseOf(reader.readLine());

        Headers headers = readHeaders(reader);

        RequestBody requestBody = readRequestBody(reader, headers);

        return new HttpRequest(requestLine, headers, requestBody);
    }

    private static Headers readHeaders(BufferedReader reader) throws IOException {
        List<String> headerLines = new ArrayList<>();

        String line = reader.readLine();
        headerLines.add(line);

        while (line != null && !"".equals(line)) {
            line = reader.readLine();
            headerLines.add(line);
        }

        return Headers.parseOf(headerLines);
    }

    private static RequestBody readRequestBody(BufferedReader reader, Headers headers) throws IOException {
        String value = headers.getValue("Content-Length");

        if (value != null && !value.equals("")) {
            return new RequestBody(IOUtils.readData(reader, Integer.parseInt(value)));
        }

        return RequestBody.EMPTY;
    }

    public String getPath() {
        return requestLine.getPath().getPath();
    }

    public String getHeader(String name) {
        return headers.getValue(name);
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getBody(String name) {
        return body.getParameter(name);
    }

    public String getCookie(String name) {
        return cookies.stream()
                .filter(cookie -> cookie.equalName(name))
                .findAny()
                .map(Cookie::getValue)
                .orElse("");
    }

    Headers getHeaders() {
        return headers;
    }

    RequestLine getRequestLine() {
        return requestLine;
    }

    RequestBody getBody() {
        return body;
    }

    String getParameter(String name) {
        String parameter = requestLine.getParameter(name);

        if (parameter != null) {
            return parameter;
        }

        return body.getParameter(name);
    }

    public void initHttpSession(HttpSession httpSession) {
        if (this.httpSession != null) {
            throw new IllegalStateException("이미 HttpSession 이 초기화 되었습니다. " + httpSession.getId());
        }

        this.httpSession = httpSession;
    }

    @Override
    public String toString() {
        return requestLine.toString() + " " + headers.toString() + " " + body;
    }
}
