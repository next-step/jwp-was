package webserver;

import webserver.http.request.HttpRequestMessage;
import webserver.http.request.header.HttpRequestHeaders;
import webserver.http.request.requestline.HttpRequestLine;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpGetMethodLinesReader implements HttpLinesReaderStrategy {
    private final BufferedReader bufferedReader;

    public HttpGetMethodLinesReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    @Override
    public HttpRequestMessage readLines(BufferedReader br, HttpRequestLine httpRequestLine) throws IOException {
        HttpRequestHeaders httpRequestHeaders = readRequestHeader(br);

        return new HttpRequestMessage(httpRequestLine, httpRequestHeaders);
    }
}
