package webserver.http.header;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class HttpCookies {
    private static final String COOKIES_DELIMITER = "; ";

    private Set<HttpCookie> cookies;

    public HttpCookies(HttpCookie... httpCookies) {
        this.cookies = new LinkedHashSet<>(Arrays.asList(httpCookies));
    }

    public HttpCookies(String rawCookieValue) {
        this.cookies = toHttpCookies(rawCookieValue);
    }

    public void addCookie(HttpCookie httpCookie) {
        if (isExistCookie(httpCookie)) {
            cookies.remove(httpCookie);
        }

        cookies.add(httpCookie);
    }

    public HttpCookie getCookie(String cookieKey) {
        return cookies.stream()
                .filter(it -> it.equals(new HttpCookie(cookieKey, "")))
                .findAny()
                .orElse(HttpCookie.NONE);
    }

    private boolean isExistCookie(HttpCookie httpCookie) {
        return cookies.contains(httpCookie);
    }


    private Set<HttpCookie> toHttpCookies(String rawCookieValue) {
        String[] rawCookieValueSchemas = rawCookieValue.split(COOKIES_DELIMITER);

        return Arrays.stream(rawCookieValueSchemas)
                .map(HttpCookie::fromRawCookie)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public String rawCookies() {
        return this.cookies.stream().map(HttpCookie::getRawCookie).collect(Collectors.joining(COOKIES_DELIMITER));
    }
}
