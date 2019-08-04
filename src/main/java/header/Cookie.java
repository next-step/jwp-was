package header;

import header.setter.HeaderSetter;
import response.HeaderResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static request.UriSplitter.SPLIT_KEY_VALUE;

/**
 * Created by youngjae.havi on 2019-08-03
 */
public class Cookie implements HeaderSetter<Cookie>, HeaderResponse {
    public static final String LOGIN_KEY = "logined";

    private Map<String, String> cookieMap = new HashMap<>();

    public Cookie() {}

    private Cookie(Map<String, String> cookieMap) {
        this.cookieMap = cookieMap;
    }

    public static HeaderResponse logined(boolean isLogined) {
        Map<String, String> cookieMap = new HashMap<>();
        cookieMap.put(LOGIN_KEY, String.valueOf(isLogined));
        cookieMap.put("path", "/");
        return new Cookie(cookieMap);
    }

    public String get(String key) {
        return cookieMap.get(key);
    }

    public boolean isLogined() {
        String isLogined = cookieMap.get(LOGIN_KEY);
        return Boolean.parseBoolean(isLogined);
    }

    @Override
    public Cookie setEliment(String[] keyValue) {
        String[] values = keyValue[1].split(SPLIT_KEY_VALUE.getSplitter());
        IntStream.range(0, values.length)
                .filter(i -> i%2 == 0 && i+1 < values.length)
                .forEach(i -> cookieMap.put(values[i].trim(), values[i+1]));
        return this;
    }

    @Override
    public String key() {
        return "Set-Cookie";
    }

    @Override
    public String value() {
        return cookieMap.keySet().stream()
                .map(key -> key + SPLIT_KEY_VALUE.getSplitter() + cookieMap.get(key))
                .collect(Collectors.joining("; "));
    }
}
