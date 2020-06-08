package http.request;

import http.Headers;
import http.HttpSession;
import http.session.HttpSessionStorage;
import http.Method;
import http.Parameters;
import http.RequestLine;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import utils.IOUtils;
import utils.StringUtils;

public class HttpRequest {

    private final RequestLine requestLine;
    private final Headers headers;
    private final Parameters parameters;

    private final HttpSessionStorage httpSessionStorage;

    private HttpRequest(RequestLine requestLine, Headers headers, String body, HttpSessionStorage httpSessionStorage) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.httpSessionStorage = httpSessionStorage;

        this.parameters = requestLine.getParameters();
        this.parameters.addAll(headers.parseBody(body));
    }

    public static HttpRequest of(String requestLine, List<String> headers, String body, HttpSessionStorage httpSessionStorage) {
        return new HttpRequest(
            RequestLine.of(requestLine),
            Headers.from(headers),
            body,
            httpSessionStorage
        );
    }

    public static HttpRequest from(InputStream in, HttpSessionStorage httpSessionStorage) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String requestLine = br.readLine();

        List<String> headerLines = new ArrayList<>();
        String line = br.readLine();
        while (StringUtils.isNotEmpty(line)) {
            headerLines.add(line);
            line = br.readLine();
        }

        Headers headers = Headers.from(headerLines);
        String requestBody = IOUtils.readData(br, headers.getContentLength());

        return new HttpRequest(RequestLine.of(requestLine), headers, requestBody, httpSessionStorage);
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getParameter(String name) {
        return parameters.get(name);
    }

    public Method getMethod() {
        return requestLine.getMethod();
    }

    public String getCookie(String name) {
        return this.headers.getCookie(name);
    }

    public HttpSession getHttpSession(){
        return this.headers.getHttpSession(this.httpSessionStorage);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HttpRequest that = (HttpRequest) o;
        return Objects.equals(requestLine, that.requestLine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestLine);
    }
}
