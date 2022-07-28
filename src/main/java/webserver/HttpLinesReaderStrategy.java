package webserver;

import webserver.http.request.HttpRequestMessage;
import webserver.http.request.header.HttpRequestHeaders;
import webserver.http.request.requestline.HttpRequestLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface HttpLinesReaderStrategy {
    public HttpRequestMessage readLines(BufferedReader br, HttpRequestLine httpRequestLine) throws IOException;

    default HttpRequestHeaders readRequestHeader(BufferedReader br) throws IOException {
        List<String> requestHeaderLines = new ArrayList<>();

        String readLine = "";
        do {
            readLine = br.readLine();
            requestHeaderLines.add(readLine);
        } while (!readLine.isEmpty());

        return new HttpRequestHeaders(requestHeaderLines.stream()
                .filter(requestHeaderLine -> !requestHeaderLine.isEmpty())
                .collect(Collectors.toList()));
    }
}
