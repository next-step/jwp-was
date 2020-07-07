package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static http.HttpHeader.CONTENT_LENGTH;
import static http.HttpHeader.COOKIE;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.springframework.util.StringUtils.collectionToCommaDelimitedString;

public class RequestHeaders {
    private static final Logger logger = LoggerFactory.getLogger(RequestHeaders.class);

    public static final String HEADER_SEPARATOR = ": ";
    public static final String COMMA_SEPARATOR = ", ";

    private static final int KET_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private Map<String, List<String>> requestHeaders;

    public RequestHeaders() {
        this.requestHeaders = new HashMap<>();
    }

    public void addHeader(String readLine) {
        String[] tokens = readLine.split(HEADER_SEPARATOR);
        requestHeaders.put(tokens[KET_INDEX], asList(tokens[VALUE_INDEX].split(COMMA_SEPARATOR)));
    }

    public String getHeader(final String header) {
        return collectionToCommaDelimitedString(requestHeaders.get(header));
    }

    public boolean hasContentLength() {
        final String contentLength = getContentLength();
        return contentLength != null && !"".equals(contentLength);
    }

    public String getContentLength() {
        return collectionToCommaDelimitedString(requestHeaders.get(CONTENT_LENGTH.getValue()));
    }

    public String getCookie() {
        return collectionToCommaDelimitedString(requestHeaders.getOrDefault(COOKIE.getValue(), emptyList()));
    }

    public List<String> getSessionId() {
        String[] tokens = getCookie().split(";");
        logger.debug("getCookie(): {}", getCookie());
        return Stream.of(tokens)
                .map(String::trim)
                .map(token -> token.split("="))
                .filter(values -> values.length == 2)
                .filter(values -> values[0].equals("JSESSIONID"))
                .map(values -> values[1])
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "RequestHeaders{" +
                "requestHeaders=" + requestHeaders +
                '}';
    }

}
