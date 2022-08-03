package model;

import java.util.List;

public class HttpHeader {

    public static final String DELIMITER = ":";
    public static final int NAME_INDEX = 0;
    public static final int VALUE_INDEX = 1;
    public static final int NO_CONTENT = 0;
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final int REQUEST_LINE_INDEX = 0;
    public static final String COOKIE = "Cookie";
    public static final String NO_COOKIE = null;

    private List<String> headers;

    public HttpHeader(List<String> headers) {
        this.headers = headers;
    }

    public int getContentLength() {
        return headers.stream()
                .map(header -> header.split(DELIMITER))
                .filter(header -> header[NAME_INDEX].equals(CONTENT_LENGTH))
                .map(header -> Integer.parseInt(header[VALUE_INDEX].strip()))
                .findFirst()
                .orElse(NO_CONTENT);
    }

    public String getRequestLine() {
        return headers.get(REQUEST_LINE_INDEX);
    }

    public String getCookie() {
        return headers.stream()
                .map(header -> header.split(DELIMITER))
                .filter(header -> header[NAME_INDEX].equals(COOKIE))
                .map(header -> header[VALUE_INDEX].strip())
                .findFirst()
                .orElse(NO_COOKIE);
    }
}
