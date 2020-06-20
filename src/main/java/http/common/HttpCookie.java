package http.common;

import utils.StringUtils;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpCookie {

    private final Map<String, String> cookies;

    public HttpCookie(String cookieContent) {
        this.cookies = parse(cookieContent);
    }

    private Map<String, String> parse(String cookieContent) {
        if(cookieContent == null) {
            return new HashMap<>();
        }
        String[] pairs = cookieContent.split(";");
        return Arrays.stream(pairs)
                .map(pair -> StringUtils.splitIntoPair(pair, "="))
                .collect(Collectors.toMap(e -> e[0].trim(), e -> e[1].trim()));
    }

    public String get(String name) {
        return this.cookies.get(name);
    }

    public int size() {
        return this.cookies.size();
    }
}
