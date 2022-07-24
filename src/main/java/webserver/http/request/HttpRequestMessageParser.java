package webserver.http.request;

import webserver.http.request.header.HttpRequestHeaders;
import webserver.http.request.requestline.HttpRequestLine;

import java.util.ArrayList;
import java.util.List;

public class HttpRequestMessageParser {
    private static final int HTTP_REQUEST_LINE_INDEX = 0;
    private static final int HTTP_REQUEST_HEADERS_START_INDEX = 1;

    public static HttpRequestMessage parse(List<String> rawHttpRequestLines) {

        HttpRequestLine httpRequestLine = new HttpRequestLine(rawHttpRequestLines.get(HTTP_REQUEST_LINE_INDEX));
        HttpRequestHeaders httpRequestHeaders = new HttpRequestHeaders(toExcludedRequestLine(rawHttpRequestLines));

        return new HttpRequestMessage(httpRequestLine, httpRequestHeaders);
    }

    private static List<String> toExcludedRequestLine(List<String> rawHttpRequestLines) {
        List<String> excludedRequestLine = new ArrayList<>();

        for (int i = HTTP_REQUEST_HEADERS_START_INDEX; i < rawHttpRequestLines.size(); i++) {
            excludedRequestLine.add(rawHttpRequestLines.get(i));
        }

        return excludedRequestLine;
    }
}
