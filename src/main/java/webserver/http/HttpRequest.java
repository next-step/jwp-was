package webserver.http;

import utils.FileIoUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;

public class HttpRequest {
    private final static String TEMPLATES_PREFIX = "./templates";
    private RequestLine requestLine;
    private HttpHeaders httpHeaders;

    private HttpRequest(RequestLine requestLine, HttpHeaders httpHeaders) {
        this.requestLine = requestLine;
        this.httpHeaders = httpHeaders;
    }

    public static HttpRequest parse(BufferedReader bufferedReader) throws IOException {
        RequestLine requestLine = RequestLine.parse(bufferedReader.readLine());
        HttpHeaders httpHeaders = HttpHeaders.parse(bufferedReader);

        return new HttpRequest(requestLine, httpHeaders);
    }

    public URI getUri() {
        return requestLine.getUri();
    }

    public HttpHeaders getHeaders() {
        return this.httpHeaders;
    }

    public HttpResponse doGet() throws IOException, URISyntaxException {
        byte[] body = "Hello World".getBytes();
        String requestPath = requestLine.getUri().getPath();
        if (requestPath.endsWith(".html"))
            body = FileIoUtils.loadFileFromClasspath(TEMPLATES_PREFIX + requestPath);

        return new HttpResponse(body);
    }
}
