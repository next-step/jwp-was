package webserver.http.header;

import org.junit.jupiter.api.Test;

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
}
