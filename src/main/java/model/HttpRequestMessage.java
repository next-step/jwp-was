package model;

import service.RequestService;
import utils.HttpParser;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpRequestMessage {

    private final String CONTENT_LENGTH_KEY = "Content-Length";

    private final RequestLine requestLine;

    private HttpHeaders requestHeaders;

    private HttpBody body;

    public HttpRequestMessage(BufferedReader bufferedReader) throws IOException {
        this(RequestService.getHttpMessageData(bufferedReader), bufferedReader);
    }

    public HttpRequestMessage(List<String> httpMessageData) throws IOException {
        this(httpMessageData, null);
    }

    public HttpRequestMessage(List<String> httpMessageData, BufferedReader bufferedReader) throws IOException {
        if (!(httpMessageData instanceof ArrayList)) {
            httpMessageData = new ArrayList<>(httpMessageData);
        }

        if (httpMessageData.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.requestLine = HttpParser.parseRequestLine(httpMessageData.remove(0));
        if (httpMessageData.isEmpty()) {
            return;
        }

        this.requestHeaders = new HttpHeaders(httpMessageData);
        this.body = new HttpBody(parseBody(bufferedReader, requestHeaders));
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public HttpHeaders getRequestHeaders() {
        return requestHeaders;
    }

    public String getBody() {
        return this.body.getContents();
    }

    public String toStringHttpMessage() {
        StringBuilder value = new StringBuilder();
        value.append("[" + "\n");
        value.append(this.requestLine.getInfo());
        value.append(this.requestHeaders.getInfo());
        value.append("\n");
        value.append(this.getBody());
        value.append("\n");
        value.append("]");

        return value.toString();
    }

    private String parseBody(BufferedReader bufferedReader, HttpHeaders requestHeaders) throws IOException {
        if (bufferedReader == null) {
            return null;
        }

        String contentLengthValue = requestHeaders
                .getHeaders()
                .get(CONTENT_LENGTH_KEY);
        int contentLength = this.getContentLength(contentLengthValue);
        if (contentLength == 0) {
            return null;
        }

        return IOUtils.readData(bufferedReader, contentLength);
    }

    private int getContentLength(String contentLengthValue) {
        if (contentLengthValue != null) {
            return Integer.parseInt(contentLengthValue);
        }

        return 0;
    }

}
