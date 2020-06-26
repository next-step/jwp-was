package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HttpRequest {

    private RequestLine requestLine;
    private RequestHeaders requestHeaders;

    public HttpRequest(InputStream inputStream) throws IOException {
        buildHttpRequest(inputStream);
    }

    private void buildHttpRequest(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

        this.requestLine = RequestLineParser.parse(bufferedReader.readLine());
        String readLine = bufferedReader.readLine();
        System.out.println("readLine: " + readLine);
        List<RequestHeader> requestHeaders = new ArrayList<>();

        while (!"".equals(readLine)) {
            requestHeaders.add(RequestHeadersParser.parse(readLine));
            readLine = bufferedReader.readLine();
        }

        this.requestHeaders = new RequestHeaders(requestHeaders);
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public RequestHeaders getRequestHeaders() {
        return requestHeaders;
    }
}
