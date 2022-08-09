package webserver.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Headers {
    private static final String COOKIE_KEY = "Cookie";
    private static final Pattern COOKIE_PATTERN = Pattern.compile("(?:; )?([^;]+)=([^;]+)");
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final Map<String, Object> headers;
    private final Map<String, Object> cookies;

    public Headers() {
        this(new HashMap<>());
    }

    public Headers(Map<String, Object> headers) {
        this.headers = headers;
        this.cookies = makeCookies(headers);
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public <T> T getHeader(String key, Class<T> returnType) {
        return OBJECT_MAPPER.convertValue(headers.get(key), returnType);
    }

    public String getHeader(String key) {
        return getHeader(key, String.class);
    }

    public boolean hasHeader(String key) {
        return headers.containsKey(key);
    }

    public void setHeader(String key, Object value) {
        headers.put(key, value);
    }

    public <T> T getCookie(String key, Class<T> returnType) {
        return OBJECT_MAPPER.convertValue(cookies.get(key), returnType);
    }

    public String getCookie(String key) {
        return getCookie(key, String.class);
    }

    public void setCookie(String key, Object value) {
        cookies.put(key, value);
    }

    private Map<String, Object> makeCookies(Map<String, Object> headers) {
        final Object cookie = headers.get(COOKIE_KEY);
        if (cookie == null) {
            return new HashMap<>();
        }
        return HttpUtils.parseParameters((String) cookie, COOKIE_PATTERN);
    }
}
