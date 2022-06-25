package webserver.request;

import utils.CookieUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Headers {

    public static final String COOKIE_KEY_NAME = "Cookie";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String LOCATION = "Location";

    private final Map<String, String> headerMap;

    public Headers(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    public static Headers empty() {
        return new Headers(new HashMap<>());
    }

    public static Headers from(BufferedReader br) throws IOException {
        String line = null;
        Headers headers = Headers.empty();
        while (!"".equals(line)) {
            line = br.readLine();
            if (Objects.isNull(line)) {
                break;
            }

            headers.addHeaderByLine(line);
        }
        return headers;
    }

    private void addHeaderByLine(String line) {
        String[] split = line.split(": ", 2);
        if (split.length < 2) {
            return;
        }

        headerMap.put(split[0], split[1]);
    }

    public String get(String s) {
        return headerMap.get(s);
    }

    public int getContentLength() {
        String contentLength = headerMap.get(CONTENT_LENGTH);
        try {
            return Integer.parseInt(contentLength);
        } catch (Exception e) {
        }
        return 0;
    }

    public void put(String key, String value) {
        headerMap.put(key, value);
    }

    public void putCookie(String key, String value) {
        Map<String, String> cookies = CookieUtils.strToCookieMap(headerMap.get(COOKIE_KEY_NAME));
        cookies.put(key, value);
        String cookieMapStr = CookieUtils.cookieMapToStr(cookies);
        headerMap.put(COOKIE_KEY_NAME, cookieMapStr);
    }

    public String getCookie(String key) {
        Map<String, String> cookies = CookieUtils.strToCookieMap(headerMap.get(COOKIE_KEY_NAME));
        return cookies.getOrDefault(key, null);
    }

    public String response() {
        return headerMap.entrySet().stream()
                .map(entry -> {
                    String key = entry.getKey();
                    if (key == "Cookie") {
                        key = "Set-Cookie";
                    }
                    return key + ": " + entry.getValue();
                })
                .collect(Collectors.joining("\r\n")) + "\r\n";
    }
}
