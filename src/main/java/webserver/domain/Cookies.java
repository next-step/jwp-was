package webserver.domain;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Cookies {

    private static final String COOKIE_STRING_SPLIT_REGEX = ";";
    private List<Cookie> cookies;

    public Cookies(List<Cookie> cookies) {
        this.cookies = cookies;
    }

    public static Cookies of(String cookieString) {
        if (Strings.isNullOrEmpty(cookieString)) {
            return new Cookies(Lists.newArrayList());
        }
        return new Cookies(Arrays.stream(cookieString.split(COOKIE_STRING_SPLIT_REGEX))
            .map(String::trim)
            .map(Cookie::requestCookie)
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
            .filter(s -> s.isSessionId(HttpHeader.JSESSIONID))
            .map(Cookie::getValue)
            .collect(Collectors.joining());
    }
}
