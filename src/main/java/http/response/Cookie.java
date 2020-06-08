package http.response;

import org.apache.logging.log4j.util.Strings;

public class Cookie {
    private String cookie;
    private String path;
    private String httpOnly;

    public Cookie(String cookie, String path, boolean isHttpOnly) {
        this.cookie = cookie;
        this.path = path;
        this.httpOnly = assignHttpOnlyValue(isHttpOnly);
    }

    public static Cookie parse(String value) {
        String[] split = value.split("; ");
        String cookie = split[0];
        String path = split[1].split("=")[1];

        if (split.length == 2) {
            return new Cookie(cookie, path, false);
        }

        return new Cookie(cookie, path, true);
    }

    @Override
    public String toString() {
        return cookie + "; "
                + "Path=" + path + "; "
                + httpOnly;
    }

    private String assignHttpOnlyValue(boolean isHttpOnly) {
        if (isHttpOnly) {
            return "HttpOnly";
        }
        return Strings.EMPTY;
    }
}
