package webserver.http.header;

public class HttpCookie {
    public static final HttpCookie NONE = new HttpCookie("", "");
    public static final HttpCookie ALL_ALLOWED_PATH = new HttpCookie("Path", "/");
    private static final String COOKIE_DELIMITER = "=";
    public static final int COOKIE_SCHEMA_REQUIRED_LENGTH = 2;
    public static final int COOKIE_KEY_SCHEMA_INDEX = 0;
    public static final int COOKIE_VALUE_SCHEMA_INDEX = 1;
    private String cookieKey;

    private String cookieValue;

    public HttpCookie(String cookieKey, String cookieValue) {
        this.cookieKey = cookieKey;
        this.cookieValue = cookieValue;
    }

    public String getRawCookie() {
        return cookieKey + COOKIE_DELIMITER + cookieValue;
    }

    public static HttpCookie fromRawCookie(String rawCookie) {
        if (rawCookie == null || rawCookie.isEmpty()) {
            return NONE;
        }

        String[] rawCookieSchemas = rawCookie.split(COOKIE_DELIMITER);

        if (rawCookieSchemas.length != COOKIE_SCHEMA_REQUIRED_LENGTH) {
            return NONE;
        }

        String cookieKey = rawCookieSchemas[COOKIE_KEY_SCHEMA_INDEX];
        String cookieValue = rawCookieSchemas[COOKIE_VALUE_SCHEMA_INDEX];

        return new HttpCookie(cookieKey, cookieValue);
    }

    public boolean isNone() {
        return this == NONE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HttpCookie that = (HttpCookie) o;

        return cookieKey != null ? cookieKey.equals(that.cookieKey) : that.cookieKey == null;
    }

    @Override
    public int hashCode() {
        return cookieKey != null ? cookieKey.hashCode() : 0;
    }

    public String getCookieValue() {
        return cookieValue;
    }
}
