package webserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestFactory {
    private static final Logger logger = LoggerFactory.getLogger(RequestFactory.class);

    private RequestFactory() {}

    public static Request createRequest(BufferedReader br) throws IOException {
        RequestLine requestLine = createRequestLine(br);
        RequestHeader requestHeader = createRequestHeader(br);
        RequestBody requestBody = createRequestBody(br, requestHeader);
        return new Request(requestLine, requestHeader, requestBody);
    }

    private static RequestLine createRequestLine(BufferedReader br) throws IOException {
        String requestLine = br.readLine();
        logger.info(requestLine);
        return RequestLine.from(requestLine);
    }

    private static RequestHeader createRequestHeader(BufferedReader br) throws IOException {
        List<String> headerLines = new ArrayList<>();
        for (String headerLine = br.readLine();
             !headerLine.equals("");
             headerLine = br.readLine()
        ) {
            logger.info(headerLine);
            headerLines.add(headerLine);
        }
        return RequestHeader.from(headerLines);
    }

    private static RequestBody createRequestBody(BufferedReader br, RequestHeader requestHeader) throws IOException {
        int contentLength = requestHeader.getContentLength();
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        String requestBody = String.copyValueOf(body);
        logger.info(requestBody);
        return RequestBody.from(requestBody);
    }
}
