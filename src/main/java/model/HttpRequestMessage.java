package model;

import service.RequestService;
import utils.HttpParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpRequestMessage {

    private RequestLine requestLine;

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
        this.body = new HttpBody(bufferedReader, requestHeaders);
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

}
