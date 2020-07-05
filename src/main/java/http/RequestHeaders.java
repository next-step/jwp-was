package http;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.util.StringUtils.collectionToCommaDelimitedString;

public class RequestHeaders {

    public static final String HEADER_SEPARATOR = ": ";
    public static final String COMMA_SEPARATOR = ", ";

    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String ACCEPT = "Accept";

    private Map<String, List<String>> requestHeaders;

    public RequestHeaders() {
        this.requestHeaders = new HashMap<>();
    }

    public void addHeader(String readLine) {
        String[] tokens = readLine.split(HEADER_SEPARATOR);
        requestHeaders.put(tokens[0], Arrays.asList(tokens[1].split(COMMA_SEPARATOR)));
    }

    public String getHeader(final String header) {
        return collectionToCommaDelimitedString(requestHeaders.get(header));
    }

    public boolean hasContentLength() {
        final String contentLength = getContentLength();
        return contentLength != null && !"".equals(contentLength);
    }

    public String getContentLength() {
        return collectionToCommaDelimitedString(requestHeaders.get(CONTENT_LENGTH));
    }

    public String getCookie() {
        return collectionToCommaDelimitedString(requestHeaders.get(HttpHeader.COOKIE.getValue()));
    }

    public String getAccept() {
        return collectionToCommaDelimitedString(requestHeaders.get("Accept"));
    }

    @Override
    public String toString() {
        return "RequestHeaders{" +
                "requestHeaders=" + requestHeaders +
                '}';
    }

}
