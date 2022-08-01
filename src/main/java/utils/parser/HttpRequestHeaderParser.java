package utils.parser;

import enums.HttpMethod;
import model.HttpHeader;
import model.request.HttpRequestHeader;
import model.request.RequestLine;
import utils.BufferedReaderUtils;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static model.request.HttpRequestHeader.getRequestHeaderOf;
import static model.request.HttpRequestHeader.postRequestHeaderWithBody;

public class HttpRequestHeaderParser {
    private static final int REQUEST_LINE_INDEX = 0;
    private static final String END_OF_HTTP_REQUEST_HEADER = "";

    public static HttpRequestHeader parseHttpRequestHeaderParser(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        List<String> httpRequestHeaders = BufferedReaderUtils.lines(br);

        RequestLine requestLine = RequestLineParser.parse(httpRequestHeaders.get(REQUEST_LINE_INDEX));
        HttpHeader httpHeader = HttpHeaderParser.parseHeader(extractHttpHeaders(httpRequestHeaders));

        return createHttpRequestHeader(requestLine, httpHeader, br);
    }

    private static List<String> extractHttpHeaders(List<String> httpRequestHeaderLines) {
        httpRequestHeaderLines.remove(REQUEST_LINE_INDEX);
        httpRequestHeaderLines.remove(END_OF_HTTP_REQUEST_HEADER);

        return httpRequestHeaderLines;
    }

    private static HttpRequestHeader createHttpRequestHeader(RequestLine requestLine, HttpHeader httpHeader, BufferedReader br) throws IOException {
        if (requestLine.getHttpMethod() == HttpMethod.GET) {
            return getRequestHeaderOf(requestLine, httpHeader);
        }

        if (requestLine.getHttpMethod() == HttpMethod.POST) {
            int contentLength = Integer.parseInt(httpHeader.getValueByKey("Content-Length"));
            Map<String, String> httpBody = QueryStringParser.parse(IOUtils.readData(br, contentLength));

            return postRequestHeaderWithBody(requestLine, httpHeader, httpBody);
        }

        return null;
    }
}
