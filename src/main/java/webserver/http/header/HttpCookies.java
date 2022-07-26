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

    public void addCookie(HttpCookie httpCookie) {
        if (isExistCookie(httpCookie)) {
            cookies.remove(httpCookie);
        }

        cookies.add(httpCookie);
    }

    private boolean isExistCookie(HttpCookie httpCookie) {
        return cookies.contains(httpCookie);
    }

    public String rawCookies() {
        return this.cookies.stream().map(HttpCookie::getRawCookie).collect(Collectors.joining(COOKIES_DELIMITER));
    }
}
