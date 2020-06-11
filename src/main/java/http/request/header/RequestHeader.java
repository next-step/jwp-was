package http.request.header;

import java.util.Map;
import java.util.Objects;

public class RequestHeader {

    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String COOKIE = "Cookie";
    private static final String SEPARATOR = ":";
    private static final int MIN_CONTENT_LENGTH = 0;

    private Map<String, String> header;

    private RequestHeaderContentLength requestHeaderContentLength;
    private RequestHeaderCookie requestHeaderCookie;

    public RequestHeader(final Map<String, String> header) {
        this.header = header;
    }

    public String getHeader(String key) {
        return header.get(key);
    }

    public boolean isContainsCookie() {
        return header.containsKey(COOKIE);
    }

    private boolean isContainsContentLength() {
        return header.containsKey(CONTENT_LENGTH);
    }

    public boolean isContentLength() {
        return Objects.nonNull(requestHeaderContentLength);
    }

    private boolean isCookie() {
        return Objects.nonNull(requestHeaderCookie);
    }

    public int getContentLength2() {
        if (isContainsContentLength()) {
            return Integer.parseInt(header.get(CONTENT_LENGTH));
        }
        return 0;
    }

    public int getContentLength() {
        if (isContentLength()) {
            return requestHeaderContentLength.getContentLength();
        }
        return MIN_CONTENT_LENGTH;
    }

    public boolean isLogined() {
        if (isCookie()) {
            return requestHeaderCookie.isLogined();
        }
        return false;
    }

}
