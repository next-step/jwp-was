package http.request.header;

import java.util.Map;

public class RequestHeader {

    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String COOKIE = "Cookie";

    private Map<String, String> header;

    private RequestHeaderCookie requestHeaderCookie;

    public RequestHeader(final Map<String, String> header) {
        this.header = header;
        requestHeaderCookie = new RequestHeaderCookie(header.getOrDefault(COOKIE, ""));
    }

    public String getHeader(String key) {
        return header.get(key);
    }

    public String getSessionId() {
        return requestHeaderCookie.getSessionId();
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
