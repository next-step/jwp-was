package http.response;

import org.apache.logging.log4j.util.Strings;

import java.util.Objects;

public class Cookie {
    private static final String REGEX_COOKIE_DELIMITER = "; ";
    private static final String REGEX_KEY_VALUE_DELIMITER = "=";
    private static final int INDEX_FIRST = 0;
    private static final int INDEX_SECOND = 1;
    private static final int LENGTH_TWO = 2;

    private String cookie;
    private String path;
    private String httpOnly;

    public Cookie(String cookie, String path, boolean isHttpOnly) {
        this.cookie = cookie;
        this.path = path;
        this.httpOnly = assignHttpOnlyValue(isHttpOnly);
    }

    public static Cookie parse(String value) {
        String[] split = value.split(REGEX_COOKIE_DELIMITER);
        String cookie = split[INDEX_FIRST];
        String path = split[INDEX_SECOND].split(REGEX_KEY_VALUE_DELIMITER)[INDEX_SECOND];

        if (split.length == LENGTH_TWO) {
            return new Cookie(cookie, path, false);
        }

        return new Cookie(cookie, path, true);
    }

    public String getCookie() {
        return cookie;
    }

    public String getPath() {
        return path;
    }

    public String getHttpOnly() {
        return httpOnly;
    }

    private String assignHttpOnlyValue(boolean isHttpOnly) {
        if (isHttpOnly) {
            return "HttpOnly";
        }
        return Strings.EMPTY;
    }

    @Override
    public String toString() {
        return cookie + "; "
                + "Path=" + path + "; "
                + httpOnly;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cookie cookie1 = (Cookie) o;
        return Objects.equals(cookie, cookie1.cookie) &&
                Objects.equals(path, cookie1.path) &&
                Objects.equals(httpOnly, cookie1.httpOnly);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cookie, path, httpOnly);
    }
}
