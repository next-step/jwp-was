package webserver.http.domain;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest {
    private final RequestHeader requestHeader;
    private final RequestLine requestLine;
    private final RequestBody requestBody;

    public HttpRequest(BufferedReader br) {
        try {
            String url = br.readLine();
            RequestHeader requestHeader = new RequestHeader();
            requestHeader.addRequestHeaders(br);

            this.requestHeader = requestHeader;
            this.requestLine = new RequestLine(url);
            this.requestBody = new RequestBody(br, requestHeader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public RequestHeader requestHeader() {
        return requestHeader;
    }

    public RequestBody requestBody() {
        return requestBody;
    }

    public String path() {
        return this.requestLine.path();
    }

    public boolean startsWith(String prefix) {
        return this.requestLine.startsWith(prefix);
    }

    public boolean endsWith(String suffix) {
        return this.requestLine.endsWith(suffix);
    }
}
