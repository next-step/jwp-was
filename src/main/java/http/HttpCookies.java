package http;

import com.google.common.collect.Maps;
import utils.StringParseUtils;

import java.util.Collection;
import java.util.Map;

public class HttpCookies {
    private final Map<String, String> values;

    private HttpCookies(final Map<String, String> values) {
        this.values = values;
    }

    public static HttpCookies from(final String cookieStr) {
        return new HttpCookies(StringParseUtils.parseCookies(cookieStr));
    }

    public static HttpCookies create() {
        return new HttpCookies(Maps.newHashMap());
    }

    public String getValue(final String key) {
        return values.getOrDefault(key, null);
    }

    public void addCookie(final String key, final String value) {
        values.put(key, value);
    }

    public Collection<String> getCookieNames() {
        return values.keySet();
    }
}
