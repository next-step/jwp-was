package webserver.request.domain.request;

import webserver.exception.NotFoundRequestLineException;

public class HttpRequest {
    private int lineCount = 0;

    private RequestLine requestLine;
    private RequestHeader requestHeader;

    public HttpRequest() {
        requestHeader = new RequestHeader();
    };

    public void addProperty(String line) {
        if(line == null) {
            return;
        }

        if(lineCount == 0) {
            requestLine = RequestLine.parse(line);
            lineCount += 1;
            return;
        }

        requestHeader.addHeaderProperty(line);
        lineCount += 1;
    }

    public String getPath() {
        if(requestLine == null) {
            throw new NotFoundRequestLineException("requestLine 객체가 null 입니다.");
        }

        return requestLine.parsePath();
    }

    public String getRequestLine() {
        return requestLine.toString();
    }
}
