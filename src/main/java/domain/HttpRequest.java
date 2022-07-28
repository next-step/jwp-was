package domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpUtils;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class HttpRequest {
    public static final String VALIDATION_MESSAGE = "잘못된 HTTP 요청입니다.";
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequest.class);
    private static final Pattern HEADER_DELIMITER = Pattern.compile(": ");
    private static final String PAYLOAD_DELIMITER = "&";
    private static final String PAYLOAD_ITEM_DELIMITER = "=";
    private static final int CORRECT_LENGTH = 2;

    private final RequestLine requestLine;
    private final Map<String, String> headers;
    private final Map<String, String> payloads;

    public HttpRequest(BufferedReader br) {
        this.requestLine = makeRequestLine(br);
        this.headers = makeHeaders(br);
        this.payloads = makePayloads(br);
    }

    private RequestLine makeRequestLine(BufferedReader br) {
        final String line = readLine(br);
        validate(line);
        return new RequestLine(line);
    }

    private Map<String, String> makeHeaders(BufferedReader br) {
        Map<String, String> headers = new HashMap<>();
        String line;
        while (!"".equals(line = readLine(br))) {
            LOGGER.info("line: {}", line);
            final String[] splitLine = HEADER_DELIMITER.split(line);
            headers.put(splitLine[0], splitLine[1]);
        }
        return headers;
    }

    private String readLine(BufferedReader br) {
        String line;
        try {
            line = br.readLine();
        } catch (IOException e) {
            throw new IllegalArgumentException(VALIDATION_MESSAGE, e);
        }
        return line;
    }

    private void validate(String line) {
        if (line == null) {
            throw new IllegalArgumentException(VALIDATION_MESSAGE);
        }
    }

    private Map<String, String> makePayloads(BufferedReader br) {
        if (requestLine.isGET()) {
            return new HashMap<>();
        }

        try {
            final int contentLength = getContentLength();
            final String payload = IOUtils.readData(br, contentLength);
            return makePayloads(payload);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, String> makePayloads(String payload) {
        final Map<String, String> payloads = new HashMap<>();
        final String[] splitPayloadSpecs = payload.split(PAYLOAD_DELIMITER);
        for (String splitPayloadSpec : splitPayloadSpecs) {
            addQueryString(payloads, splitPayloadSpec);
        }
        return payloads;
    }

    private void addQueryString(Map<String, String> queryStrings, String splitQueryStringSpec) {
        final String[] querystringItems = splitQueryStringSpec.split(PAYLOAD_ITEM_DELIMITER);
        validateQueryStringItems(querystringItems);
        queryStrings.put(querystringItems[0], HttpUtils.decode(querystringItems[1]));
    }

    private void validateQueryStringItems(String[] items) {
        if (items.length != CORRECT_LENGTH) {
            throw new IllegalArgumentException(VALIDATION_MESSAGE);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpRequest that = (HttpRequest) o;
        return Objects.equals(requestLine, that.requestLine) && Objects.equals(headers, that.headers) && Objects.equals(payloads, that.payloads);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestLine, headers, payloads);
    }

    public RequestLine makeRequestLine() {
        return requestLine;
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public int getContentLength() {
        try {
            return Integer.parseInt(getHeader("Content-Length"));
        } catch (Exception e) {
            return 0;
        }
    }

    public Map<String, String> getPayloads() {
        return payloads;
    }
}
