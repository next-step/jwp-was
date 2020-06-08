package utils;

import http.request.Headers;
import http.request.Request;
import http.request.RequestBody;
import http.request.RequestLine;
import org.apache.logging.log4j.util.Strings;
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
    private static final int COUNT_ZERO = 0;
    private static final int COUNT_ONE = 1;
    private static final int COUNT_TWO = 2;
    private static final int LENGTH_TWO = 2;
    private static final int INDEX_FIRST = 0;
    private static final int INDEX_SECOND = 1;

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
        if (count != COUNT_ZERO) {
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
        if (count != COUNT_ONE) {
            throw new IllegalArgumentException("RequestLine should be serviced prior to getting Headers!");
        }

        Map<String, String> headers = new HashMap<>();
        String header = br.readLine();

        while (!Strings.EMPTY.equals(header)) {
            String[] split = header.split(REGEX_HEADER_DELIMITER);
            if (split.length == LENGTH_TWO) {
                headers.put(split[INDEX_FIRST], split[INDEX_SECOND]);
            }
            header = br.readLine();
        }

        count++;
        this.headers = new Headers(headers);
        return Collections.unmodifiableMap(headers);
    }

    private String getBodyAsString() throws IOException {
        if (count != COUNT_TWO) {
            throw new IllegalArgumentException("RequestHeaders should be serviced prior to getting RequestBody");
        }

        String contentLength = headers.getHeader("Content-Length");
        if (contentLength == null) {
            return Strings.EMPTY;
        }

        return IOUtils.readData(br, Integer.parseInt(contentLength));
    }
}
