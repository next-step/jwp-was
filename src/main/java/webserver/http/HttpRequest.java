package webserver.http;

import utils.IOUtils;
import utils.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static webserver.http.ContentType.APPLICATION_X_WWW_FORM_URLENCODED;

public class HttpRequest {

    private static final String HEADER_DELIMITER = ":";
    private static final String WHITESPACE_REGEX = "\\s*";
    private static final String HEADER_KEY_VALUE_DELIMITER = WHITESPACE_REGEX + HEADER_DELIMITER + WHITESPACE_REGEX;
    private static final int HEADER_SPLIT_LIMIT = 1;

    private RequestLine requestLine;
    private Map<String, String> headers;
    private Map<String, String> body;

    private HttpRequest(RequestLine requestLine, Map<String, String> headers, Map<String, String> body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
    }

    public static HttpRequest parse(BufferedReader br) throws IOException {
        RequestLine requestLine = RequestLine.parse(br.readLine());
        Map<String, String> headers = parseHeaders(br);
        Map<String, String> body = parseBodyIfNotGet(br, requestLine, headers);
        return new HttpRequest(requestLine, headers, body);
    }

    private static Map<String, String> parseHeaders(BufferedReader br) throws IOException {
        Map<String, String> headers = new HashMap<>();
        for (String line = br.readLine(); StringUtils.isNotEmpty(line); line = br.readLine()) {
            StringPair pair = StringPair.split(line, HEADER_KEY_VALUE_DELIMITER, HEADER_SPLIT_LIMIT);
            headers.put(pair.getKey(), pair.getValue());
        }
        return headers;
    }

    private static Map<String, String> parseBodyIfNotGet(BufferedReader br,
                                                         RequestLine requestLine,
                                                         Map<String, String> headers) throws IOException {
        if (requestLine.getMethod() == HttpMethod.GET) {
            return Collections.emptyMap();
        }

        String body = IOUtils.readData(br, Integer.valueOf(headers.get("Content-Length")));
        if (ContentType.parse(headers.get("Content-Type")) == APPLICATION_X_WWW_FORM_URLENCODED) {
            body = URLDecoder.decode(body, "UTF-8");
        }
        RequestParameter requestBody = RequestParameter.parse(body);
        return requestBody.getParameters();
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

    public Map<String, String> getBody() {
        return body;
    }
}
