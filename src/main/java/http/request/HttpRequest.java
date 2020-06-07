package http.request;

import http.Headers;
import http.Method;
import http.Parameters;
import http.RequestLine;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import utils.IOUtils;
import utils.StringUtils;

public class HttpRequest {

    private final RequestLine requestLine;
    private final Headers headers;
    private final String body;

    private final Parameters parameters;

    private HttpRequest(RequestLine requestLine, Headers headers, String body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
        this.parameters = requestLine.getParameters();
        this.parameters.addAll(headers.parseBody(body));
    }

    public static HttpRequest of(String requestLine, List<String> headers, String body) {
        return new HttpRequest(
            RequestLine.of(requestLine),
            Headers.from(headers),
            body
        );
    }

    public static HttpRequest from(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String requestLine = br.readLine();

        List<String> headerLines = new ArrayList<>();
        String line = br.readLine();
        while(StringUtils.isNotEmpty(line)){
            headerLines.add(line);
            line = br.readLine();
        }

        Headers headers = Headers.from(headerLines);
        String requestBody = IOUtils.readData(br, headers.getContentLength());

        return new HttpRequest(RequestLine.of(requestLine), headers, requestBody);
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
        return this.headers.getCookies().get(name);
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
