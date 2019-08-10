package webserver.request;

import java.util.*;

/**
 * Created by hspark on 2019-08-04.
 */
public class RequestHeaders {
    private static final String HEADER_DELIMITER = ":";
    private static final int BEGIN_INDEX = 0;
    public static final String COMMA = ",";
    public static final String ACCEPT_NAME = "Accept";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String COOKIE = "Cookie";

    private Map<String, String> headers = new HashMap<>();
    private Cookies cookies = new Cookies();

    public String getHeader(String name) {
        return headers.get(name);
    }

    public Cookies getCookies() {
        return cookies;
    }

    public String getCookie(String name) {
        return cookies.getCookie(name);
    }

    public boolean hasContentLength() {
        return Objects.nonNull(headers.get(CONTENT_LENGTH));
    }

    public void add(String rawHeader) {
        int delimiterIndex = rawHeader.indexOf(HEADER_DELIMITER);
        String name = rawHeader.substring(BEGIN_INDEX, delimiterIndex);
        String value = rawHeader.substring(delimiterIndex + 1);
        if (COOKIE.equals(name)){
            this.cookies = Cookies.parse(value);
            return;
        }
        headers.put(name.trim(), value.trim());
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getAccept() {
        return headers.get(ACCEPT_NAME);
    }

    public String getContentTypeByAccept() {
        return getAccept().split(COMMA)[0];
    }
}
