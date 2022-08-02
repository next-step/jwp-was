package webserver.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Cookies {

    private static final String COOKIE_STRING_SPLIT_REGEX = ";";
    private List<String> cookies;

    public Cookies(List<String> cookies) {
        this.cookies = cookies;
    }

    public static Cookies of(String cookieString) {
        return new Cookies(Arrays.stream(cookieString.split(COOKIE_STRING_SPLIT_REGEX))
            .map(String::trim)
            .collect(Collectors.toUnmodifiableList()));
    }

    public static Cookies empty() {
        return new Cookies(Collections.emptyList());
    }

    public int size() {
        return cookies.size();
    }

    public String getSessionId() {
        return cookies.stream()
            .filter(s -> s.startsWith(HttpHeader.JSESSIONID))
            .map(s -> s.split("=")[1])
            .collect(Collectors.joining());
    }
}
