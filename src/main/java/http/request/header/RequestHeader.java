package http.request.header;

import java.util.Map;

public class RequestHeader {

    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String COOKIE = "Cookie";

    private Map<String, String> header;

    private RequestHeaderCookie requestHeaderCookie;

    public RequestHeader(final Map<String, String> header) {
        this.header = header;
    }

    public String getHeader(String key) {
        return header.get(key);
    }

    public boolean isLogin() {
        if (isContainsCookie()) {
            requestHeaderCookie = new RequestHeaderCookie(header.getOrDefault(COOKIE, ""));
            return requestHeaderCookie.isLogined();
        }
        return false;
    }

    private boolean isContainsCookie() {
        return header.containsKey(COOKIE);
    }

    private boolean isContainsContentLength() {
        return header.containsKey(CONTENT_LENGTH);
    }

    public int getContentLength() {
        if (isContainsContentLength()) {
            return Integer.parseInt(header.get(CONTENT_LENGTH));
        }
        return 0;
    }
}
