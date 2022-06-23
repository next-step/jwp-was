package webserver.http;

import webserver.http.request.Cookies;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Header {
    private static final String CONTENT_LENGTH = "Content-Length";
    private final Map<String, String> values;

    private final Map<String, String> cookies;

    public Header(Map<String, String> values, Map<String, String> cookies) {
        this.values = values;
        this.cookies = cookies;
    }

    public static Header of(BufferedReader bufferedReader) throws IOException {
        Map<String, String> values = new HashMap<>();
        Map<String, String> cookies = new HashMap<>();

        String readLine;
        do {
            readLine = bufferedReader.readLine();
            if (readLine == null || "".equals(readLine)) {
                break;
            }
            String[] tokens = readLine.split(": ");
            if (tokens[0].equals("Cookie")) {
                createCookies(cookies, tokens[1]);
            }
            values.put(tokens[0], tokens[1]);
        } while (!"".equals(readLine));

        return new Header(values, cookies);
    }

    private static void createCookies(Map<String, String> cookies, String cookieValues) {
        String[] tokens = cookieValues.split("; ");
        Arrays.stream(tokens).forEach(token -> {
            String[] keyValues = token.split("=");
            cookies.put(keyValues[0], keyValues[1]);
        });
    }

    public int getContentLength() {
        return values.get(CONTENT_LENGTH) == null ? 0 : Integer.valueOf(values.get(CONTENT_LENGTH));
    }

    public String get(String key) {
        return values.get(key);
    }

    public void put(String key, String value) {
        values.put(key, value);
    }

    public String getCookie(String key) {
        return cookies.get(key);
    }

    public List<String> toHeaderStrings() {
        return values.keySet()
                               .stream()
                               .map(key -> String.format("%s: %s", key, values.get(key)))
                               .collect(Collectors.toList());
    }
}
