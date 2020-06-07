package http.request;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
public class RequestHeader {
    private static final String LINE_REGEX = ": ";
    private static final String ACCEPT_HEADER = "Accept";
    private static final String ACCEPT_HEADER_REGEX = ",";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String COOKIE_HEADER = "Cookie";

    private Map<String, String> headerMap;
    private RequestCookie requestCookie;

    public RequestHeader(Map<String, String> headerMap, RequestCookie requestCookie) {
        this.headerMap = headerMap;
        this.requestCookie = requestCookie;
    }

    private RequestHeader(Map<String, String> headerMap) {
        this(headerMap, null);
    }

    public static RequestHeader getInstance(BufferedReader br) throws IOException {
        Map<String, String> headerMap = new HashMap<>();

        String line;
        while (!(line = br.readLine()).equals("")) {
            String[] headerValues = line.split(LINE_REGEX);
            headerMap.put(headerValues[0], headerValues[1].trim());
        }

        if (headerMap.containsKey(COOKIE_HEADER)) {
            return new RequestHeader(headerMap, RequestCookie.getInstance(headerMap.get(COOKIE_HEADER)));
        }

        return new RequestHeader(headerMap);
    }

    public String getValue(String key) {
        return headerMap.get(key);
    }

    public String getContentType() {
        String[] acceptValues = getValue(ACCEPT_HEADER).split(ACCEPT_HEADER_REGEX);
        return acceptValues[0];
    }

    public int getContentLength() {
        return Integer.parseInt(getValue(CONTENT_LENGTH));
    }

    public boolean loggedIn() {
        if (Objects.isNull(this.requestCookie)) {
            return false;
        }

        return this.requestCookie.loggedIn();
    }
}
