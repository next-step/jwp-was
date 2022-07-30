package webserver.http;

import java.util.HashMap;
import java.util.Map;

public class RequestHeader {

    private static final String COOKIE = "Cookie";
    private static final String HEADER_DELIMETER = ": ";
    private static final String EMPTY_HEADER_VALUE = "";

    private Map<String, String> headers = new HashMap<>();

    public void addHeader(String requestHeaderLine) {
        if (requestHeaderLine == null) {
            return;
        }

        String[] splitedHeader = requestHeaderLine.split(HEADER_DELIMETER);
        if (splitedHeader.length == 2) {
            this.headers.put(splitedHeader[0].trim(), splitedHeader[1].trim());
        }
    }

    public void addHeader(String contentTypeKey, String contentTypeValue) {
        this.headers.put(contentTypeKey, contentTypeValue);
    }

    public String getHeader(String contentTypeKey) {
        return this.headers.getOrDefault(contentTypeKey, EMPTY_HEADER_VALUE);
    }

    public int getContentLength() {
        int defaultContentLength = 0;
        String contentLength = headers.get("Content-Length");
        if (contentLength != null) {
            defaultContentLength = Integer.parseInt(contentLength);
        }
        return defaultContentLength;
    }

    public Cookies getCookie() {
        return new Cookies(getHeader(COOKIE));
    }
}
