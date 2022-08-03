package webserver.http.request.header;

import java.util.HashMap;
import java.util.Map;

public class HttpHeader {
    private static final String EMPTY_STRING = "";
    private static final String HEADER_FIELD_DELIMITER = ": ";
    private static final String COOKIE_DELIMITER = "=";
    private static final String CONTENT_LENGTH_STRING = "Content-Length";
    private static final String COOKIE_STRING = "Cookie";
    private static final String ZERO_STRING = "0";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private Map<String, String> field;
    private Cookie cookie;

    public HttpHeader(Map<String, String> field, Cookie cookie) {
        this.field = field;
        this.cookie = cookie;
    }

    public HttpHeader() {
        this(new HashMap<>(), new Cookie());
    }

    public void setField(String headerString) {
        validateHeaderString(headerString);
        String[] fieldElements = headerString.split(HEADER_FIELD_DELIMITER);
        field.put(fieldElements[KEY_INDEX], fieldElements[VALUE_INDEX]);
    }

    public void setCookie(String cookieString) {
        validateCookieString(cookieString);
        String[] cookieElements = cookieString.split(COOKIE_DELIMITER);
        if (!cookieString.isEmpty()) {
            this.cookie = new Cookie(cookieElements[KEY_INDEX], cookieElements[VALUE_INDEX]);
        }
    }

    public int getContentLength() {
        return Integer.parseInt(this.field.getOrDefault(CONTENT_LENGTH_STRING, ZERO_STRING));
    }

    public String getCookieValue() {
        return this.field.getOrDefault(COOKIE_STRING, EMPTY_STRING);
    }

    public boolean isLogin() {
        return Boolean.parseBoolean(this.cookie.getValue());
    }

    private void validateHeaderString(String headerString) {
        if (headerString == null || headerString.isEmpty()) {
            throw new IllegalArgumentException("요청된 HTTP Header 는 비어있거나 null 일 수 없습니다.");
        }
    }

    private void validateCookieString(String cookieString) {
        if (cookieString == null) {
            throw new IllegalArgumentException("요청된 HTTP Header 의 cookie 는 null 일 수 없습니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HttpHeader header = (HttpHeader) o;

        if (!field.equals(header.field)) return false;
        return cookie.equals(header.cookie);
    }

    @Override
    public int hashCode() {
        int result = field.hashCode();
        result = 31 * result + cookie.hashCode();
        return result;
    }
}
