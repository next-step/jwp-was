package http.requests;

import http.requests.parameters.Cookie;
import http.requests.parameters.FormData;
import http.types.HttpMethod;
import utils.HttpHeader;
import utils.HttpRequestParser;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HttpRequest {

    private final RequestLine requestLine;
    private final HttpRequestHeader requestHeaders;
    private final String body;
    private final FormData formData;
    private final Cookie cookie;

    public HttpRequest(InputStream in) {
        try {
            final BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            this.requestLine = new RequestLine(br.readLine());
            this.requestHeaders = parseRequestHeaders(br);
            this.body = parseBody(br);
            this.formData = HttpRequestParser.parseFormData(body);
            this.cookie = HttpRequestParser.parseCookie(requestHeaders.getHeader(HttpHeader.COOKIE));
        } catch (IOException e) {
            throw new IllegalArgumentException("Parameter is wrong!");
        }
    }

    private HttpRequestHeader parseRequestHeaders(BufferedReader br) throws IOException {
        final List<String> rawRequestHeaders = new ArrayList<>();
        String readLine;
        while (!(readLine = br.readLine()).equals("")) {
            rawRequestHeaders.add(readLine);
        }
        return HttpRequestParser.parseHeaders(rawRequestHeaders);
    }

    private String parseBody(BufferedReader br) throws IOException {
        final String contentLength = requestHeaders.getHeader(HttpHeader.CONTENT_LENGTH);
        if (contentLength != null) {
            return IOUtils.readData(br, Integer.parseInt(contentLength));
        }
        return null;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "requestLine=" + requestLine +
                ", requestHeaders=" + requestHeaders +
                ", body='" + body + '\'' +
                ", formData=" + formData +
                ", cookie=" + cookie +
                '}';
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getAttributeFromForm(String key) {
        return formData.get(key);
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getBody() {
        return body;
    }

    public Cookie getCookie() {
        return cookie;
    }

}
