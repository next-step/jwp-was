package webserver.http.request;

import com.google.common.base.Charsets;
import webserver.http.HttpBody;
import webserver.http.HttpHeaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequest {

    private final RequestLine requestLine;
    private final HttpHeaders httpHeaders;
    private final HttpBody httpBody;

    private HttpRequest(RequestLine requestLine, HttpHeaders httpHeaders, HttpBody httpBody) {
        this.requestLine = requestLine;
        this.httpHeaders = httpHeaders;
        this.httpBody = httpBody;
    }

    public static HttpRequest parseInputStream(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charsets.UTF_8));
        String line = bufferedReader.readLine();

        RequestLine requestLine = RequestLine.parse(line);
        HttpHeaders httpHeaders = new HttpHeaders(bufferedReader);

        if (httpHeaders.hasContent()) {
            HttpBody httpBody = HttpBody.of(bufferedReader, httpHeaders.getContentLength());
            return new HttpRequest(requestLine, httpHeaders, httpBody);
        }
        return new HttpRequest(requestLine, httpHeaders, HttpBody.empty());
    }

    public boolean isStaticResource() {
        return requestLine.isStaticResource();
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    public HttpBody getHttpBody() {
        return httpBody;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "requestLine=" + requestLine +
                '}';
    }
}
