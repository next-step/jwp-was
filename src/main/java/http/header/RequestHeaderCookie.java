package http.header;

import http.CookieTranslator;

public class RequestHeaderCookie {
    private final CookieTranslator cookieTranslator;

    public RequestHeaderCookie(final String cookieValues) {
        this.cookieTranslator = new CookieTranslator(cookieValues);
    }

    public boolean isLogined() {
        return cookieTranslator.isLogined();
    }
}
