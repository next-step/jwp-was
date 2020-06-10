package http.header;

import java.util.Objects;

public class RequestHeader {

    private static final String CONTENT_LENGTH = "Content-Length:.*";
    private static final String COOKIE = "Cookie:.*";
    private static final String SEPARATOR = ":";
    private static final int MIN_CONTENT_LENGTH = 0;

    private final String readLine;

    private RequestHeaderContentLength requestHeaderContentLength;
    private RequestHeaderCookie requestHeaderCookie;

    public RequestHeader(final String readLine) {
        this.readLine = readLine;
        validate();
    }

    private void validate() {
        if (readLine.matches(CONTENT_LENGTH)) {
            String[] lengths = readLine.split(SEPARATOR);
            this.requestHeaderContentLength = new RequestHeaderContentLength(lengths[1].trim());
        }

        if (readLine.matches(COOKIE)) {
            String[] cookies = readLine.split(SEPARATOR);
            String cookieValues = cookies[1];
            this.requestHeaderCookie = new RequestHeaderCookie(cookieValues.trim());
        }
    }

    public boolean isContentLength() {
        return Objects.nonNull(requestHeaderContentLength);
    }

    private boolean isCookie() {
        return Objects.nonNull(requestHeaderCookie);
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
