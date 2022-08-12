package model;

import utils.IOUtils;
import webserver.RequestLine;

import java.io.*;
import java.util.Map;

public class HttpRequest {

    private HttpHeader header;
    private RequestLine requestLine;
    private RequestBody body;
    private Map<String, Cookie> cookie;

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

        this.requestLine = new RequestLine(IOUtils.readRequestData(br));
        this.header = new HttpHeader(IOUtils.readHeaderData(br));
        this.cookie = Cookie.createCookie(header);
        this.body = new RequestBody(getBody(br, header.getValueToInt("Content-Length")));
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    private String getBody(BufferedReader br, int contentLength) throws IOException {
        return IOUtils.readData(br, contentLength);
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public RequestBody getBody() {
        return body;
    }

    public Cookie getCookie(String name) {
        return cookie.get(name);
    }

    public String getHeader(String name) {
        return header.getValue(name);
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "requestLine=" + requestLine +
                ", body=" + body +
                ", cookie=" + cookie +
                '}';
    }

    public String getPath() {
        return requestLine.getRequestPath();
    }

    public String getParameter(String param) throws UnsupportedEncodingException {
        if (requestLine.getMethod() == HttpMethod.GET) {
            return requestLine.getRequestParams(param);
        }
        return body.getFirstValue(param);
    }
}
