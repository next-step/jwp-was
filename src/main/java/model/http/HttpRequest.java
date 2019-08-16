package model.http;

import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HttpRequest {
    private HttpRequestHeader httpRequestHeader;
    private HttpRequestBody httpRequestBody;

    private HttpRequest(HttpRequestHeader httpRequestHeader) {
        this.httpRequestHeader = httpRequestHeader;
    }

    private HttpRequest(HttpRequestHeader httpRequestHeader, HttpRequestBody httpRequestBody) {
        this.httpRequestHeader = httpRequestHeader;
        this.httpRequestBody = httpRequestBody;
    }

    public static HttpRequest of(InputStream in) throws IOException {
        HttpRequestHeader httpRequestHeader;

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        List<String> headerLines = new ArrayList<>();
        String line;

        while (!StringUtils.isEmpty(line = reader.readLine())) {
            headerLines.add(line);
        }

        httpRequestHeader = HttpRequestHeader.of(headerLines);

        if (httpRequestHeader.containsBody()) {
            HttpRequestBody httpRequestBody = HttpRequestBody.of(reader, httpRequestHeader.getContentLength());
            return new HttpRequest(httpRequestHeader, httpRequestBody);
        }

        return new HttpRequest(httpRequestHeader);
    }

    public static HttpRequest of(HttpRequestHeader httpRequestHeader) {
        return new HttpRequest(httpRequestHeader);
    }

    public HttpRequestHeader getHttpRequestHeader() {
        return httpRequestHeader;
    }

    public HttpRequestBody getHttpRequestBody() {
        return httpRequestBody;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpRequest that = (HttpRequest) o;
        return Objects.equals(httpRequestHeader, that.httpRequestHeader) &&
                Objects.equals(httpRequestBody, that.httpRequestBody);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpRequestHeader, httpRequestBody);
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "httpRequestHeader=" + httpRequestHeader +
                ", httpRequestBody=" + httpRequestBody +
                '}';
    }
}
