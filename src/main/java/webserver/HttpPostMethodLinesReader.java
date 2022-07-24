package webserver;

import utils.IOUtils;
import webserver.http.request.HttpRequestMessage;
import webserver.http.request.body.HttpRequestBody;
import webserver.http.request.header.HttpRequestHeaders;
import webserver.http.request.requestline.HttpRequestLine;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpPostMethodLinesReader implements HttpLinesReaderStrategy {
    private final BufferedReader bufferedReader;

    public HttpPostMethodLinesReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    @Override
    public HttpRequestMessage readLines(BufferedReader br, HttpRequestLine httpRequestLine) throws IOException {
        HttpRequestHeaders httpRequestHeaders = readRequestHeader(br);

        int contentLength = httpRequestHeaders.contentLength();
        HttpRequestBody httpRequestBody = new HttpRequestBody(IOUtils.readData(br, contentLength));

        return new HttpRequestMessage(httpRequestLine, httpRequestHeaders, httpRequestBody);
    }
}
