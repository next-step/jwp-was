package webserver.http.request;

import utils.IOUtils;
import webserver.http.request.header.HttpHeader;
import webserver.http.request.requestline.Method;
import webserver.http.request.requestline.QueryString;
import webserver.http.request.requestline.RequestLine;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest {
    private static final String EMPTY_STRING = "";
    private RequestLine requestLine;
    private HttpHeader header = new HttpHeader();
    private QueryString body = new QueryString();

    public HttpRequest() {
    }

    public HttpRequest(RequestLine requestLine, HttpHeader header, QueryString body) {
        this.requestLine = requestLine;
        this.header = header;
        this.body = body;
    }

    public static HttpRequest of(BufferedReader br) throws IOException {
        HttpRequest httpRequest = new HttpRequest();

        initRequestLine(br, httpRequest);
        initHeader(br, httpRequest);
        if (httpRequest.isMethodEqual(Method.POST)) {
            initBody(br, httpRequest);
        }

        return httpRequest;
    }

    private static void initBody(BufferedReader br, HttpRequest httpRequest) throws IOException {
        int contentLength = httpRequest.header.getContentLength();
        String bodyString = IOUtils.readData(br, contentLength);
        httpRequest.body = QueryString.parse(bodyString);
    }

    private static void initRequestLine(BufferedReader br, HttpRequest httpRequest) throws IOException {
        String line = br.readLine();
        if (line == null) { // 요청 line 이 null 일 경우 parse 하지 않음.
            return;
        }
        httpRequest.requestLine = RequestLine.parse(line);
    }

    private static void initHeader(BufferedReader br, HttpRequest httpRequest) throws IOException {
        String line = br.readLine();
        HttpHeader header = httpRequest.header;
        while (!line.equals(EMPTY_STRING)) {
            header.setField(line);
            line = br.readLine();
        }
        header.setCookie(header.getCookieValue());
    }

    public boolean isMethodEqual(Method method) {
        return this.requestLine.isMethodEqual(method);
    }

    public boolean isFilePath() {
        return this.requestLine.isFilePath();
    }

    public String getPath() {
        return this.requestLine.getPath();
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
