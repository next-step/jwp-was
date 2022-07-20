package request;

import java.io.BufferedReader;
import java.io.IOException;

public class Request {
    private RequestLine requestLine;
    private RequestHeader requestHeader;
    private RequestBody requestBody;

    public Request(RequestLine requestLine, RequestHeader requestHeader, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public static Request parsing(BufferedReader br) throws IOException {
        RequestLine requestLine = RequestLine.getInstance().parsing(br);
        RequestHeader requestHeader = RequestHeader.getInstance().parsing(br);
        RequestBody requestBody = RequestBody.getInstance().parsing(br, requestHeader.getContentLength());

        return new Request(requestLine, requestHeader, requestBody);
    }
}
