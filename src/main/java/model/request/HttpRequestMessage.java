package model.request;


import enums.HttpMethod;
import model.HttpHeader;
import utils.BufferedReaderUtils;
import utils.IOUtils;
import utils.parser.UrlKeyValueParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class HttpRequestMessage {
    private static final int REQUEST_LINE_INDEX = 0;
    private static final String END_OF_HTTP_REQUEST_HEADER = "";
    private final RequestLine requestLine;
    private final HttpHeader httpHeader;
    private final Map<String, String> requestBody;

    private HttpRequestMessage(RequestLine requestLine, HttpHeader httpHeader, Map<String, String> requestBody) {
        this.requestLine = requestLine;
        this.httpHeader = httpHeader;
        this.requestBody = requestBody;
    }

    public static HttpRequestMessage getRequestHeaderOf(RequestLine requestLine, HttpHeader httpHeader) {
        return new HttpRequestMessage(requestLine, httpHeader, null);
    }

    public static HttpRequestMessage postRequestHeaderWithBody(RequestLine requestLine, HttpHeader httpHeader, Map<String, String> requestBody) {
        return new HttpRequestMessage(requestLine, httpHeader, requestBody);
    }

    public static HttpRequestMessage from(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        List<String> httpRequestHeaders = BufferedReaderUtils.lines(br);

        RequestLine requestLine = new RequestLine(httpRequestHeaders.get(REQUEST_LINE_INDEX));
        HttpHeader httpHeader = new HttpHeader.Builder()
            .addHeaders(extractHttpHeaders(httpRequestHeaders))
            .build();

        return createHttpRequestHeader(requestLine, httpHeader, br);
    }

    private static List<String> extractHttpHeaders(List<String> httpRequestHeaderLines) {
        httpRequestHeaderLines.remove(REQUEST_LINE_INDEX);
        httpRequestHeaderLines.remove(END_OF_HTTP_REQUEST_HEADER);

        return httpRequestHeaderLines;
    }

    private static HttpRequestMessage createHttpRequestHeader(RequestLine requestLine, HttpHeader httpHeader, BufferedReader br) throws IOException {
        if (requestLine.getHttpMethod() == HttpMethod.GET) {
            return getRequestHeaderOf(requestLine, httpHeader);
        }

        if (requestLine.getHttpMethod() == HttpMethod.POST) {
            int contentLength = Integer.parseInt(httpHeader.getValueByKey("Content-Length"));
            Map<String, String> httpBody = UrlKeyValueParser.parse(IOUtils.readData(br, contentLength));

            return postRequestHeaderWithBody(requestLine, httpHeader, httpBody);
        }

        return null;
    }

    public Boolean isEqualPath(String path) {
        return requestLine.getPath().equals(path);
    }

    public boolean hasCookie(String cookie) {
        return httpHeader.hasCookie(cookie);
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public HttpHeader getHttpHeader() {
        return httpHeader;
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public Map<String, String> getRequestBody() {
        return requestBody;
    }

    public String getParameter(String parameterKey) {
        return requestLine.getParameter(parameterKey);
    }

    public HttpMethod getHttpMethod() {
        return requestLine.getHttpMethod();
    }
}
