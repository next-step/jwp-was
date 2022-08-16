package webserver.http.header;

import webserver.http.header.type.EntityHeader;
import webserver.http.header.type.RequestHeader;
import webserver.http.header.type.ResponseHeader;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Header {
    private static final String EMPTY_STRING = "";
    private static final String HEADER_FIELD_DELIMITER = ": ";
    private static final String ZERO_STRING = "0";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private Map<HeaderKey, String> fields;
    private Cookie cookie;

    public Header(Map<HeaderKey, String> fields) {
        this(fields, new Cookie());
    }

    public Header() {
        this(new HashMap<>());
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

    public static Header templateResponse(String sessionId) {
        Map<HeaderKey, String> fields = new HashMap<>();
        setContentTypeAndCookie(fields, HeaderValue.TEXT_HTML_UTF8, sessionId);
        return new Header(fields);
    }

    public static Header staticResponse(String sessionId) {
        Map<HeaderKey, String> fields = new HashMap<>();
        setContentTypeAndCookie(fields, HeaderValue.TEXT_CSS_UTF8, sessionId);
        return new Header(fields);
    }

    public static Header loginFailResponse(String sessionId) {
        Map<HeaderKey, String> fields = new HashMap<>();
        setContentTypeAndCookie(fields, HeaderValue.TEXT_HTML_UTF8, sessionId);
        return new Header(fields);
    }

    public static Header loginSuccessResponse(String sessionId) {
        Map<HeaderKey, String> fields = new HashMap<>();
        setContentTypeAndCookie(fields, HeaderValue.TEXT_HTML_UTF8, sessionId);
        return new Header(fields);
    }

    private static void setContentTypeAndCookie(Map<HeaderKey, String> fields, String contentType, String sessionId) {
        fields.put(EntityHeader.CONTENT_TYPE, contentType);
        fields.put(ResponseHeader.SET_COOKIE, String.format(HeaderValue.JSESSION_ID, sessionId));
    }

    public Header add(HeaderKey key, String value) {
        this.fields.put(key, value);
        return new Header(this.fields);
    }

    public void addField(String headerString) {
        validateHeaderString(headerString);
        String[] fieldElements = headerString.split(HEADER_FIELD_DELIMITER);
        this.fields.put(HeaderKey.valueOfKey(fieldElements[KEY_INDEX]), fieldElements[VALUE_INDEX]);
    }

    public void addField(HeaderKey key, String value) {
        this.fields.put(key, value);
    }

    public void setCookies(String cookieString) {
        this.cookie = Cookie.parse(cookieString);
    }

    public void setCookie(String key, String value) {
        this.cookie.setCookie(key, value);
    }

    public int getContentLength() {
        return Integer.parseInt(this.fields.getOrDefault(EntityHeader.CONTENT_LENGTH, ZERO_STRING));
    }

    public String getCookieValue() {
        return this.fields.getOrDefault(RequestHeader.COOKIE, EMPTY_STRING);
    }

    public String getCookieValue(String key) {
        return this.cookie.getValue(key);
    }

    public boolean isHeaderValueEqual(HeaderKey key, String value) {
        return Optional.ofNullable(this.fields.get(key)).map(headerValue -> headerValue.equals(value)).orElse(false);
    }

    private void validateHeaderString(String headerString) {
        if (headerString == null || headerString.isEmpty()) {
            throw new IllegalArgumentException("요청된 HTTP Header 는 비어있거나 null 일 수 없습니다.");
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