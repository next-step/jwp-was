package http.httprequest;

import http.HttpSession;
import http.SessionStorage;
import http.httprequest.requestbody.RequestBody;
import http.httprequest.requestheader.RequestHeader;
import http.httprequest.requestline.RequestLine;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class HttpRequest {
    private final RequestLine requestLine;
    private final RequestHeader httpRequestHeader;
    private final RequestBody requestBody;

    public HttpRequest(RequestLine requestLine,
                       RequestHeader httpRequestHeader,
                       RequestBody requestBody) {
        this.requestLine = requestLine;
        this.httpRequestHeader = httpRequestHeader;
        this.requestBody = requestBody;
    }

    public static HttpRequest from(BufferedReader br) throws IOException {
        List<String> input = IOUtils.readLines(br);
        RequestLine requestLine = RequestLine.from(input.get(0));
        RequestHeader requestHeader = RequestHeader.from(input.subList(0, input.size()));
        RequestBody requestBody = new RequestBody(IOUtils.readData(br, requestHeader.getContentLength()));

        return new HttpRequest(
                requestLine,
                requestHeader,
                requestBody
        );
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public int getContentLength() {
        return httpRequestHeader.getContentLength();
    }

    public Optional<String> getCookieValue(String name) {
        return httpRequestHeader.getCookieValue(name);
    }

    public HttpRequest deleteHeader(HttpRequest httpRequest, String key) {
        return new HttpRequest(
                httpRequest.requestLine,
                httpRequestHeader.delete(key),
                httpRequest.requestBody
        );
    }

    public HttpSession getSession() {
        return getCookieValue(httpRequestHeader.getSessionKey())
                .flatMap(SessionStorage.getInstance()::find)
                .orElseGet(HttpSession::empty);
    }

    public String getBodyValue(String key) {
        return requestBody.getValue(key).orElseThrow(() -> new IllegalArgumentException("해당 값이 없습니다."));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpRequest that = (HttpRequest) o;
        return requestLine.equals(that.requestLine) && httpRequestHeader.equals(that.httpRequestHeader) && requestBody.equals(that.requestBody);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestLine, httpRequestHeader, requestBody);
    }
}
