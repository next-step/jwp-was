package utils.parser;

import model.HttpHeader;
import model.HttpRequestHeader;
import model.RequestLine;
import utils.BufferedReaderUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class HttpRequestHeaderParser {
    private static final int REQUEST_LINE_INDEX = 0;
    private static final String END_OF_HTTP_REQUEST_HEADER = "";

    public static HttpRequestHeader parseHttpRequestHeaderParser(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        List<String> httpRequestHeaders = BufferedReaderUtils.lines(br);

        RequestLine requestLine = RequestLineParser.parse(httpRequestHeaders.get(REQUEST_LINE_INDEX));
        HttpHeader httpHeader = HttpHeaderParser.parseHeader(extractHttpHeaders(httpRequestHeaders));

        return new HttpRequestHeader(requestLine, httpHeader);
    }

    private static List<String> extractHttpHeaders(List<String> httpRequestHeaderLines) {
        httpRequestHeaderLines.remove(REQUEST_LINE_INDEX);
        httpRequestHeaderLines.remove(END_OF_HTTP_REQUEST_HEADER);

        return httpRequestHeaderLines;
    }
}
