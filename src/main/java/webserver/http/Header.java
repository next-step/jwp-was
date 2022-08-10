package webserver.http;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public class Header {
    private static final String EMPTY_STRING = "";
    private static final String HEADER_FIELD_DELIMITER = ": ";
    private static final String COOKIE_DELIMITER = "=";
    private static final String ZERO_STRING = "0";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private Map<HeaderKey, String> fields;
    private Cookie cookie;

    public Header(Map<HeaderKey, String> fields) {
        this(fields, new Cookie());
    }

    public Header() {
        this(new EnumMap<>(HeaderKey.class));
    }

    public Header(Map<HeaderKey, String> fields, Cookie cookie) {
        validate(fields, cookie);
        this.fields = fields;
        this.cookie = cookie;
    }

    private void validate(Map<HeaderKey, String> fields, Cookie cookie) {
        validateFields(fields);
        validateCookie(cookie);
    }

    private void validateCookie(Cookie cookie) {
        if (cookie == null) {
            throw new IllegalArgumentException("전달 받은 쿠키는 null 일 수 없습니다.");
        }
    }

    private void validateFields(Map<HeaderKey, String> fields) {
        if (fields == null) {
            throw new IllegalArgumentException("전달받은 헤더 필드는 null 일 수 없습니다.");
        }
    }

    public static Header templateResponse() {
        Map<HeaderKey, String> fields = new EnumMap<>(HeaderKey.class);
        fields.put(HeaderKey.CONTENT_TYPE, HeaderValue.TEXT_HTML_UTF8);
        return new Header(fields);
    }

    public static Header staticResponse() {
        Map<HeaderKey, String> fields = new EnumMap<>(HeaderKey.class);
        fields.put(HeaderKey.CONTENT_TYPE, HeaderValue.TEXT_CSS_UTF8);
        return new Header(fields);
    }

    public static Header loginFailResponse() {
        Map<HeaderKey, String> fields = new EnumMap<>(HeaderKey.class);
        fields.put(HeaderKey.CONTENT_TYPE, HeaderValue.TEXT_HTML_UTF8);
        fields.put(HeaderKey.SET_COOKIE, HeaderValue.LOGINED_FALSE_ALL_PATH);
        return new Header(fields);
    }

    public static Header loginSuccessResponse() {
        Map<HeaderKey, String> fields = new EnumMap<>(HeaderKey.class);
        fields.put(HeaderKey.CONTENT_TYPE, HeaderValue.TEXT_HTML_UTF8);
        fields.put(HeaderKey.SET_COOKIE, HeaderValue.LOGINED_TRUE_ALL_PATH);
        return new Header(fields);
    }

    public Header add(HeaderKey key, String value) {
        this.fields.put(key, value);
        return new Header(this.fields);
    }

    public void addField(String headerString) {
        validateHeaderString(headerString);
        String[] fieldElements = headerString.split(HEADER_FIELD_DELIMITER);
        fields.put(HeaderKey.valueOfKey(fieldElements[KEY_INDEX]), fieldElements[VALUE_INDEX]);
    }

    public void setCookie(String cookieString) {
        validateCookieString(cookieString);
        String[] cookieElements = cookieString.split(COOKIE_DELIMITER);
        if (!cookieString.isEmpty()) {
            this.cookie = new Cookie(cookieElements[KEY_INDEX], cookieElements[VALUE_INDEX]);
        }
    }

    public int getContentLength() {
        return Integer.parseInt(this.fields.getOrDefault(HeaderKey.CONTENT_LENGTH, ZERO_STRING));
    }

    public String getCookieValue() {
        return this.fields.getOrDefault(HeaderKey.COOKIE, EMPTY_STRING);
    }

    public boolean isLogin() {
        return Boolean.parseBoolean(this.cookie.getValue());
    }

    public boolean isHeaderValueEqual(HeaderKey key, String value) {
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
        for (Map.Entry<HeaderKey, String> entry : this.fields.entrySet()) {
            sb.append(String.format("%s: %s\r\n", entry.getKey().getHeaderKey(), entry.getValue()));
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
