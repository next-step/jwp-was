package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpCookieTest {

    @DisplayName("Http Cookie parse 테스트")
    @Test
    public void http_cookie_parse_test() throws Exception {
        HttpCookie cookie = new HttpCookie("JSESSIONID=test-session-id");

        assertEquals("test-session-id", cookie.getCookie("JSESSIONID"));
    }
}