package webserver.http.request;

import utils.IOUtils;
import webserver.http.header.Header;
import webserver.http.request.requestline.Method;
import webserver.http.request.requestline.Path;
import webserver.http.request.requestline.QueryString;
import webserver.http.request.requestline.RequestLine;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest {
    private static final String EMPTY_STRING = "";

    private RequestLine requestLine;
    private Header header = new Header();
    private QueryString body = new QueryString();

    public HttpRequest(BufferedReader br) throws IOException {
        initRequestLine(br);
        initHeader(br);
        initBody(br);
    }

    public HttpRequest(RequestLine requestLine, Header header, QueryString body) {
        validate(requestLine, header, body);
        this.requestLine = requestLine;
        this.header = header;
        this.body = body;
    }

    private void validate(RequestLine requestLine, Header header, QueryString body) {
        validateRequestLine(requestLine);
        validateHeader(header);
        validateBody(body);
    }

    private void validateBody(QueryString body) {
        if (body == null) {
            throw new IllegalArgumentException("요청된 HTTP RequestBody 는 null 일 수 없습니다.");
        }
    }

    private void validateHeader(Header header) {
        if (header == null) {
            throw new IllegalArgumentException("요청된 HTTP header 는 null 일 수 없습니다.");
        }
    }

    private void validateRequestLine(RequestLine requestLine) {
        if (requestLine == null) {
            throw new IllegalArgumentException("요청된 HTTP RequestLine 은 null 일 수 없습니다.");
        }
    }

    private void initBody(BufferedReader br) throws IOException {
        int contentLength = this.header.getContentLength();
        String bodyString = IOUtils.readData(br, contentLength);
        this.body = QueryString.parse(bodyString);
    }

    private void initRequestLine(BufferedReader br) throws IOException {
        String line = br.readLine();
        if (line == null) { // 요청 line 이 null 일 경우 parse 하지 않음.
            return;
        }
        this.requestLine = RequestLine.parse(line);
    }

    private void initHeader(BufferedReader br) throws IOException {
        String line;
        while (!isEmpty(line = br.readLine())) {
            this.header.addField(line);
        }
        this.header.setCookies(this.header.getCookieValue());
    }

    private static boolean isEmpty(String line) {
        return line == null || line.equals(EMPTY_STRING);
    }

    public boolean isMethodEqual(Method method) {
        return this.requestLine.isMethodEqual(method);
    }

    public boolean isPathEqual(Path path) {
        return this.requestLine.isPathEqual(path);
    }

    public boolean isHtmlFilePath() {
        return this.requestLine.isHtmlFilePath();
    }

    public boolean isStaticFilePath() {
        return this.requestLine.isStaticFilePath();
    }

    public String getPath() {
        return this.requestLine.getPath();
    }

    public Method getMethod() {
        return this.requestLine.getMethod();
    }

    public String getParam(String key) {
        return this.body.getValue(key);
    }

    public boolean isLogin() {
        return this.header.isLogin();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HttpRequest that = (HttpRequest) o;

        if (!requestLine.equals(that.requestLine)) return false;
        if (!header.equals(that.header)) return false;
        return body.equals(that.body);
    }

    @Override
    public int hashCode() {
        int result = requestLine.hashCode();
        result = 31 * result + header.hashCode();
        result = 31 * result + body.hashCode();
        return result;
    }
}
