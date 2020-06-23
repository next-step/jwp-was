package http.common;

import utils.StringUtils;


import java.util.*;
import java.util.stream.Collectors;

public class HttpCookie {

    public static final String SESSION_ID = "JSESSIONID";
    public static final String PATH = "Path";

    public static final String SEMI_COLON = ";";
    public static final String EQUAL_SIGN = "=";
    public static final String SPACE = " ";

    private final Map<String, String> cookies;

    private HttpCookie(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public static HttpCookie createEmpty() {
        return new HttpCookie(new HashMap<>());
    }

    public static HttpCookie from(String cookieValue) {
        return new HttpCookie(parse(cookieValue));
    }

    private static Map<String, String> parse(String cookieContent) {
        if (StringUtils.isEmpty(cookieContent)) {
            return Collections.emptyMap();
        }
        String[] pairs = cookieContent.split(SEMI_COLON);
        return Arrays.stream(pairs)
                .map(pair -> StringUtils.splitIntoPair(pair, EQUAL_SIGN))
                .collect(Collectors.toMap(e -> e[0].trim(), e -> e[1].trim()));
    }

    public String getCookieValueBy(String name) {
        return this.cookies.get(name);
    }

    public void addCookieValueWithPath(String name, String value, String path) {
        StringBuilder sb = new StringBuilder();
        sb.append(value)
            .append(SEMI_COLON)
            .append(SPACE)
            .append(PATH)
            .append(EQUAL_SIGN)
            .append(path);
        this.cookies.put(name, sb.toString());
    }

    public int size() {
        return this.cookies.size();
    }

    public String toJoinedString() {
        return this.cookies.entrySet()
                .stream()
                .map(header -> header.getKey() + EQUAL_SIGN + header.getValue())
                .collect(Collectors.joining(SEMI_COLON));
    }
}
