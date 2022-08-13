package webserver.http.request;


import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest {

    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private final RequestBody requestBody;

    public HttpRequest(RequestLine requestLine, RequestHeader requestHeader, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public static HttpRequest parseFrom(BufferedReader br) throws IOException {
        String line = br.readLine();

        final RequestLine requestLine = RequestLine.parseFrom(line);
        final RequestHeader requestHeader = RequestHeader.parseFrom(br);

        if (requestHeader.hasBody()) {
            return new HttpRequest(requestLine, requestHeader, RequestBody.emptyInstance());
        }

        final RequestBody requestBody = RequestBody.parseFrom(
                    IOUtils.readData(br, requestHeader.getContentLength().get())
            );

        return new HttpRequest(requestLine, requestHeader, requestBody);
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }
}
