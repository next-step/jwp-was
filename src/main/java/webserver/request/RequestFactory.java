package webserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RequestFactory {
    private RequestFactory() {}

    public static Request createRequest(BufferedReader br) throws IOException {
        RequestLine requestLine = createRequestLine(br);
        RequestHeader requestHeader = createRequestHeader(br);
        RequestBody requestBody = createRequestBody(br, requestHeader);
        return new Request(requestLine, requestHeader, requestBody);
    }

    private static RequestLine createRequestLine(BufferedReader br) throws IOException {
        return RequestLine.from(br.readLine());
    }

    private static RequestHeader createRequestHeader(BufferedReader br) throws IOException {
        List<String> headerLines = new ArrayList<>();
        for (String headerLine = br.readLine();
             !headerLine.equals("");
             headerLine = br.readLine()
        ) {
            headerLines.add(headerLine);
        }
        return RequestHeader.from(headerLines);
    }

    private static RequestBody createRequestBody(BufferedReader br, RequestHeader requestHeader) throws IOException {
        int contentLength = requestHeader.getContentLength();
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return RequestBody.from(String.copyValueOf(body));
    }
}
