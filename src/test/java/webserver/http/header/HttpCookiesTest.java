package webserver.http.header;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class HttpCookiesTest {

    @Test
    void create_and_rawCookies() {
        HttpCookies httpCookies = new HttpCookies(
                new HttpCookie("logined", "true"),
                new HttpCookie("Path", "/")
        );

        assertThat(httpCookies.rawCookies()).isEqualTo("logined=true; Path=/");
    }

    @Test
    void add_and_rawCookies() {
        HttpCookies httpCookies = new HttpCookies();

        httpCookies.addCookie(new HttpCookie("logined", "true"));
        httpCookies.addCookie(new HttpCookie("Path", "/"));


        assertThat(httpCookies.rawCookies()).isEqualTo("logined=true; Path=/");
    }

    @Test
    void duplicate_add_and_rawCookies() {
        HttpCookies httpCookies = new HttpCookies();

        httpCookies.addCookie(new HttpCookie("logined", "true"));
        httpCookies.addCookie(new HttpCookie("Path", "/"));
        httpCookies.addCookie(new HttpCookie("logined", "false"));

        assertThat(httpCookies.rawCookies()).isEqualTo("Path=/; logined=false");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "logined=true; Path=/",
            "cookieKey=cookeValue",
            "a=a; b=b; c=c;"
    })
    void create_from_rawCookies(String rawCookieValue) {
        HttpCookies httpCookies = new HttpCookies(rawCookieValue);

        assertThat(httpCookies.rawCookies()).isEqualTo(rawCookieValue);
    }

    @Test
    void getCookie() {
        HttpCookies httpCookies = new HttpCookies();

        httpCookies.addCookie(new HttpCookie("logined", "true"));

        HttpCookie httpCookie = httpCookies.getCookie("logined");

        assertThat(httpCookie).isEqualTo(new HttpCookie("logined", "true"));
    }

    @Test
    void getCookie_and_none() {
        HttpCookies httpCookies = new HttpCookies();

        httpCookies.addCookie(new HttpCookie("logined", "true"));

        HttpCookie httpCookie = httpCookies.getCookie("logined2");

        assertThat(httpCookie).isEqualTo(HttpCookie.NONE);
        assertThat(httpCookie.isNone()).isTrue();
    }
}
