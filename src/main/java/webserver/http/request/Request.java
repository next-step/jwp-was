package webserver.http.request;

import webserver.http.cookie.Cookies;

import java.nio.charset.Charset;
import java.util.HashMap;

public class Request {
    private final RequestLine requestLine;
    private final Headers headers;

    private final Cookies cookies;

    public Request(RequestLine requestLine, Headers headers) {
        this(requestLine, headers, new Cookies(new HashMap<>()));
    }

    public Request(RequestLine requestLine, Headers headers, Cookies cookies) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.cookies = cookies;
    }

    public boolean hasContents() {
        return headers.containsContentLength();
    }

    public int getContentLength() {
        return headers.getContentLength();
    }

    public void addParameters(Parameters parameters) {
        requestLine.addParameters(parameters);
    }

    public boolean hasMethod(Method method) {
        return requestLine.hasMethod(method);
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public boolean hasPath(String path) {
        return getPath().equals(path);
    }

    public String getParameter(String key) {
        return requestLine.getParameter(key);
    }

    public boolean hasContentType(String contentType) {
        return headers.hasContentType(contentType);
    }

    public void decodeCharacter(Charset charset) {
        requestLine.decodeCharacter(charset);
    }

    public String getHeader(String name) {
        return headers.getValue(name);
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestLine=" + requestLine +
                ", headers=" + headers +
                '}';
    }
}
