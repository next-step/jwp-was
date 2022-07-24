package webserver;

import webserver.http.request.HttpRequestMessage;
import webserver.http.request.requestline.HttpRequestLine;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpLinesReader {
    public static HttpRequestMessage readRequestMessage(BufferedReader br) throws IOException {
        HttpRequestLine httpRequestLine = new HttpRequestLine(br.readLine());

        HttpLinesReaderStrategy httpLinesReaderStrategy = HttpLinesReaderFactory.create(br, httpRequestLine.getHttpMethod());

        return httpLinesReaderStrategy.readLines(br, httpRequestLine);
    }
}
