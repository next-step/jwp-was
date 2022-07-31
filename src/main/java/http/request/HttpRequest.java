package http.request;

import java.io.BufferedReader;
import java.util.Optional;

import utils.IOUtils;

public class HttpRequest {

    private final RequestLine requestLine;
    private final Headers headers;
    private final RequestBody body;

    public HttpRequest(RequestLine requestLine, Headers headers, String body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = new RequestBody(body);
    }

    public static HttpRequest parse(BufferedReader bufferedReader) {
        String line = IOUtils.readSingleLine(bufferedReader);
        var requestLine = new RequestLine(line);
        var headers = new Headers(IOUtils.readLines(bufferedReader));

        if (headers.hasBody()) {
            var body = IOUtils.readData(bufferedReader, headers.contentLength());
            return new HttpRequest(requestLine, headers, body);
        }

        return new HttpRequest(requestLine, headers, "");
    }

    public boolean isStaticFile() {
        return requestLine.isStaticFile();
    }

    public String getUrl() {
        return requestLine.getUrl();
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getBody() {
        return body.getValue();
    }

    public Optional<String> getCookie(String key) {
        return headers.getCookie(key);
    }

    public String getFileExtension() {
        return requestLine.getFileExtension();
    }
}
