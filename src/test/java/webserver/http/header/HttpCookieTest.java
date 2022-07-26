package webserver.http.header;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpCookieTest {

    @Test
    void getRawCookie() {
        HttpCookie httpCookie = new HttpCookie("logined", "true");

        assertThat(httpCookie.getRawCookie()).isEqualTo("logined=true");
    }

    @Test
    void equals_키가같으면_같은객체() {
        HttpCookie httpCookie = new HttpCookie("logined", "true");
        HttpCookie httpCookie2 = new HttpCookie("logined", "false");

        assertThat(httpCookie).isEqualTo(httpCookie2);
    }
}
