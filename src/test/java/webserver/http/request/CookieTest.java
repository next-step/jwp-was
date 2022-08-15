package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static webserver.http.request.Cookie.LOGINED;

public class CookieTest {

    @DisplayName("쿠키를 파싱하고, 쿠키값을 검증한다.")
    @Test
    void parseAndGetLoginCookie() {
        Cookie cookie = Cookie.parseFrom("logined=true");

        assertEquals("true", cookie.getCookies().get(LOGINED));
    }
}
