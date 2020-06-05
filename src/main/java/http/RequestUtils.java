package http;

import http.body.Body;
import http.headers.Headers2;
import http.requestline.requestLine2.RequestLine2;
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
    private int count = -1;

    public RequestUtils(BufferedReader br) {
        count++;
        this.br = br;
    }

    public RequestLine2 getRequestLine() throws IOException {
        return new RequestLine2(getRequestLineAsString());
    }

    public Headers2 getHeaders() throws IOException {
        return new Headers2(getHeadersAsMap());
    }

    public Body getBody() throws IOException {

        return new Body(getBodyAsString());
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
        return Collections.unmodifiableMap(headers);
    }

    private String getBodyAsString() throws IOException {
        if (count != 2) {
            throw new IllegalArgumentException("RequestHeaders should be serviced prior to getting RequestBody");
        }

        String body = br.readLine();
        if (body == null) {
            return "";
        }

        return body;
    }
}
