package webserver.http;

import utils.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private static final String HEADER_DELIMITER = ":";
    private static final String WHITESPACE_REGEX = "\\s*";
    private static final String HEADER_KEY_VALUE_DELIMITER = WHITESPACE_REGEX + HEADER_DELIMITER + WHITESPACE_REGEX;
    private static final int HEADER_SPLIT_LIMIT = 1;

    private RequestLine requestLine;
    private Map<String, String> headers;

    private HttpRequest(RequestLine requestLine, Map<String, String> headers) {
        this.requestLine = requestLine;
        this.headers = headers;
    }

    public static HttpRequest parse(BufferedReader br) throws IOException {
        RequestLine requestLine = RequestLine.parse(br.readLine());
        Map<String, String> headers = parseHeaders(br);
        return new HttpRequest(requestLine, headers);
    }

    private static Map<String, String> parseHeaders(BufferedReader br) throws IOException {
        Map<String, String> headers = new HashMap<>();
        for (String line = br.readLine(); StringUtils.isNotEmpty(line); line = br.readLine()) {
            StringPair pair = StringPair.split(line, HEADER_KEY_VALUE_DELIMITER, HEADER_SPLIT_LIMIT);
            headers.put(pair.getKey(), pair.getValue());
        }
        return headers;
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
