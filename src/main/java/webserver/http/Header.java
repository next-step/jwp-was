package webserver.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Header {
    private static final String EMPTY_STRING = "";
    private static final String HEADER_FIELD_DELIMITER = ": ";
    private static final String COOKIE_DELIMITER = "=";
    private static final String CONTENT_LENGTH_STRING = "Content-Length";
    private static final String COOKIE_STRING = "Cookie";
    private static final String ZERO_STRING = "0";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private Map<String, String> fields;
    private Cookie cookie;

    public Header(Map<String, String> fields, Cookie cookie) {
        this.fields = fields;
        this.cookie = cookie;
    }

    public Header(Map<String, String> fields) {
        this(fields, new Cookie());
    }

    public Header() {
        this(new HashMap<>());
    }

    public Header add(String key, String value) {
        Map<String, String> fields = new HashMap<>(this.fields);
        fields.put(key, value);
        return new Header(fields);
    }

    public void addField(String headerString) {
        validateHeaderString(headerString);
        String[] fieldElements = headerString.split(HEADER_FIELD_DELIMITER);
        fields.put(fieldElements[KEY_INDEX], fieldElements[VALUE_INDEX]);
    }

    public void setCookie(String cookieString) {
        validateCookieString(cookieString);
        String[] cookieElements = cookieString.split(COOKIE_DELIMITER);
        if (!cookieString.isEmpty()) {
            this.cookie = new Cookie(cookieElements[KEY_INDEX], cookieElements[VALUE_INDEX]);
        }
    }

    public int getContentLength() {
        return Integer.parseInt(this.fields.getOrDefault(CONTENT_LENGTH_STRING, ZERO_STRING));
    }

    public String getCookieValue() {
        return this.fields.getOrDefault(COOKIE_STRING, EMPTY_STRING);
    }

    public boolean isLogin() {
        return Boolean.parseBoolean(this.cookie.getValue());
    }

    public boolean isHeaderValueEqual(String key, String value) {
        return Optional.ofNullable(this.fields.get(key)).map(headerValue -> headerValue.equals(value)).orElse(false);
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

    public String header() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : this.fields.entrySet()) {
            sb.append(String.format("%s: %s\r\n", entry.getKey(), entry.getValue()));
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Header header = (Header) o;

        if (!fields.equals(header.fields)) return false;
        return cookie.equals(header.cookie);
    }

    @Override
    public int hashCode() {
        int result = fields.hashCode();
        result = 31 * result + cookie.hashCode();
        return result;
    }
}
