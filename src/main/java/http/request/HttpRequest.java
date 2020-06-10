package http.request;

import http.RequestLine;
import http.RequestLineParser;
import http.header.RequestHeader;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private static final String SEPARATOR_HEADER = ":";

    private RequestLine requestLine;
    private RequestHeader requestHeader;

    public HttpRequest(BufferedReader bufferedReader) throws IOException {
        String readLine = bufferedReader.readLine();
        this.requestHeader = new RequestHeader(createHeaders(bufferedReader));
        this.requestLine = parseRequestLine(bufferedReader, readLine);
    }

    private Map<String, String> createHeaders(BufferedReader bufferedReader) throws IOException {
        Map<String, String> headers = new HashMap<>();
        String readLine = bufferedReader.readLine();
        while (!"".equals(readLine)) {
            String[] lines = readLine.split(SEPARATOR_HEADER);
            headers.put(lines[0].trim(), lines[1].trim());
            readLine = bufferedReader.readLine();
            if (readLine == null) {
                break;
            }
        }
        return headers;
    }

    private RequestLine parseRequestLine(BufferedReader bufferedReader, String line) throws IOException {
        int contentLength = requestHeader.getContentLength2();
        if (requestHeader.getContentLength2() > 0) {
            String requestBody = IOUtils.readData(bufferedReader, contentLength);
            return RequestLineParser.parse(line, requestBody);
        }
        return RequestLineParser.parse(line);
    }

    public String getMethod() {
        return requestLine.getMethodName();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getHeader(String key) {
        return requestHeader.getHeader(key);
    }

    public String getParameter(String key) {
        return requestLine.getParams().get(key);
    }


}
