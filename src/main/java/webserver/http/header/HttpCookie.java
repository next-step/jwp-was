package webserver.http.header;

public class HttpCookie {
    public static final HttpCookie ALL_ALLOWED_PATH = new HttpCookie("Path", "/");
    private static final String COOKIE_DELIMITER = "=";
    private String cookieKey;
    private String cookieValue;

    public HttpCookie(String cookieKey, String cookieValue) {
        this.cookieKey = cookieKey;
        this.cookieValue = cookieValue;
    }

    public String getRawCookie() {
        return cookieKey + COOKIE_DELIMITER + cookieValue;
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
}
