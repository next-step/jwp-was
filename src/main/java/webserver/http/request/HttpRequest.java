package webserver.http.request;

import utils.IOUtils;
import webserver.http.HttpSession;
import webserver.http.SessionDatabase;
import webserver.http.header.Header;
import webserver.http.header.HeaderValue;
import webserver.http.header.type.RequestHeader;
import webserver.http.request.requestline.Method;
import webserver.http.request.requestline.Path;
import webserver.http.request.requestline.QueryString;
import webserver.http.request.requestline.RequestLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public class HttpRequest {
    private static final String EMPTY_STRING = "";
    private static final String JSESSION_ID = "JSESSIONID";

    private RequestLine requestLine;
    private Header header = new Header();
    private QueryString body = new QueryString();

    public HttpRequest(BufferedReader br) throws IOException {
        initRequestLine(br);
        initHeader(br);
        initBody(br);
        initSession();
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

    public HttpSession getSession() {
        String jSessionId = this.header.getCookieValue(JSESSION_ID);
        return SessionDatabase.findById(jSessionId);
    }

    public String getSessionId() {
        return getSession().getId();
    }

    private void initSession() {
        if (this.header.getCookieValue(JSESSION_ID).isEmpty()) {
            String newId = UUID.randomUUID().toString();
            SessionDatabase.save(newId, new HttpSession(newId));
            this.header.setCookie(JSESSION_ID, newId);
            this.header.addField(RequestHeader.COOKIE, String.format(HeaderValue.JSESSION_ID, newId));
        }
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
        Object user = getSession().getAttribute("user");
        return Optional.ofNullable(user).isPresent();
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
