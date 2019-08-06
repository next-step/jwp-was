package webserver.http.cookie;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SimpleCookie implements Cookie {

    @Getter
    private String rowCookie;

    private Map<String, String> cookies = new HashMap<>();

    public SimpleCookie(final String cookie) {
        setRowCookie(cookie);
        setCookies(cookie);
    }

    public String get(final String key) {
        return cookies.getOrDefault(key, "");
    }

    private void setRowCookie(final String rowCookie) {
        this.rowCookie = StringUtils.defaultString(rowCookie);
    }

    private void setCookies(final String cookies) {
        if (StringUtils.isBlank(cookies)) {
            return;
        }

        Arrays.stream(cookies.split(Cookie.SEMICOLON))
                .forEach(cookie -> {
                    final String[] splitCookie = splitCookie(cookie);
                    if (splitCookie == null) {
                        return;
                    }

                    final String key = parseKey(splitCookie);
                    final String value = parseValue(splitCookie);

                    this.cookies.put(key, value);
                });
    }

    private String[] splitCookie(final String cookie) {
        if (!StringUtils.contains(cookie, Cookie.EQUALS)) {
            return null;
        }

        return cookie.split(Cookie.EQUALS);
    }

    private String parseKey(final String[] splitCookie) {
        final int INDEX_OF_KEY = 0;
        return StringUtils.trim(splitCookie[INDEX_OF_KEY]);
    }

    private String parseValue(final String[] splitCookie) {
        final int INDEX_OF_VALUE = 1;
        return StringUtils.trim(splitCookie[INDEX_OF_VALUE]);
    }
}
