package http.request;

import http.cookie.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static http.cookie.Cookie.COOKIE_HEADER;
import static http.session.HttpSession.SESSION_HEADER_KEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpHeadersTest {

    @DisplayName("Cookie List 조회 후 해당 List 변경 시도 시 Exception")
    @Test
    void getCookies() {
        /* given */
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put(COOKIE_HEADER, "key1=value");

        List<Cookie> cookies = httpHeaders.getCookies();

        /* when */ /* then */
        assertThrows(UnsupportedOperationException.class, () -> cookies.remove(0));
    }

    @DisplayName("Cookie List에서 Session Id 찾기")
    @Test
    void findSessionId() {
        /* given */
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put(COOKIE_HEADER, String.format("%s=1234567", SESSION_HEADER_KEY));

        /* when */
        String sessionId = httpHeaders.findSessionId();

        /* then */
        assertThat(sessionId).isEqualTo("1234567");
    }

    @DisplayName("Cookie List에서 Session Id 찾을 때 없으면 null")
    @Test
    void findSessionId2() {
        /* given */
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put(COOKIE_HEADER, "key1=value1");

        /* when */
        String sessionId = httpHeaders.findSessionId();

        /* then */
        assertThat(sessionId).isNull();
    }
}
