package webserver.http.response.header;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : yusik
 * @date : 2019-08-10
 */
public class Cookie {

    private final static String DOMAIN_KEY = "Domain";
    private final static String PATH_KEY = "Path";
    private final static String EXPIRES_KEY = "Expires";
    private final static String COOKIE_SEPARATOR = "; ";
    private final static String COOKIE_DELIMITER = "=";

    private String name;
    private String value;
    private String domain;
    private String path;
    private String expires;

    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public static Map<String, Cookie> parse(String cookieString) {
        Map<String, Cookie> cookies = new HashMap<>();
        if (cookieString == null) {
            return cookies;
        }

        String[] tmpCookies = cookieString.split(COOKIE_SEPARATOR);
        for (String tmpCookie : tmpCookies) {
            String[] cookie = tmpCookie.trim().split(COOKIE_DELIMITER);
            cookies.put(cookie[0], new Cookie(cookie[0], cookie[1]));
        }

        return cookies;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(name + COOKIE_DELIMITER + value);
        appendIfAbsent(builder, DOMAIN_KEY, domain);
        appendIfAbsent(builder, PATH_KEY, path);
        appendIfAbsent(builder, EXPIRES_KEY, expires);
        return builder.toString();
    }

    private void appendIfAbsent(StringBuilder builder, String key, String value) {
        if (value != null) {
            builder.append(COOKIE_SEPARATOR).append(key)
                    .append(COOKIE_DELIMITER).append(value);
        }
    }
}
