package utils;

import http.request.Request;
import http.request.RequestBody;
import http.request.Headers;
import http.request.RequestLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RequestUtils {
    private static final Logger logger = LoggerFactory.getLogger(RequestUtils.class);
    private static final String REGEX_HEADER_DELIMITER = ": ";

    private BufferedReader br;
    private Headers headers;
    private int count = -1;

    public RequestUtils(BufferedReader br) {
        count++;
        this.br = br;
    }

    public Request getRequest() throws IOException {
        return new Request(getRequestLine(), getHeaders(), getBody());
    }

    public RequestLine getRequestLine() throws IOException {
        return new RequestLine(getRequestLineAsString());
    }

    public Headers getHeaders() throws IOException {
        return new Headers(getHeadersAsMap());
    }

    public RequestBody getBody() throws IOException {
        return new RequestBody(getBodyAsString());
    }

    private String getRequestLineAsString() throws IOException {
        if (count != 0) {
            throw new IllegalArgumentException("Appropriate BufferedReader is not injected!");
        }

        count++;
        String requestLine = br.readLine();
        logger.debug("RequestLine: {}", requestLine);

        if (requestLine == null) {
            throw new IllegalArgumentException("RequestLine doesn't exist!");
        }

        return requestLine;
    }

    private Map<String, String> getHeadersAsMap() throws IOException {
        if (count != 1) {
            throw new IllegalArgumentException("RequestLine should be serviced prior to getting Headers!");
        }

        Map<String, String> headers = new HashMap<>();
        String header = br.readLine();

        while (!"".equals(header)) {
            String[] split = header.split(REGEX_HEADER_DELIMITER);
            if (split.length == 2) {
                headers.put(split[0], split[1]);
            }
            header = br.readLine();
        }

        count++;
        this.headers = new Headers(headers);
        return Collections.unmodifiableMap(headers);
    }

    private String getBodyAsString() throws IOException {
        if (count != 2) {
            throw new IllegalArgumentException("RequestHeaders should be serviced prior to getting RequestBody");
        }

        String contentLength = headers.getHeader("Content-Length");
        if (contentLength == null) {
            return "";
        }

        return IOUtils.readData(br, Integer.parseInt(contentLength));
    }
}
