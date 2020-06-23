package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CookieTest {

    @DisplayName("Http header의 쿠키 값 파싱")
    @Test
    void initCookie() {

        // given
        String cookieText = "JSESSIONID=ABCD1234; Path=/";

        Map<String, String> map = new HashMap<>();
        map.put("JSESSIONID", "ABCD1234");
        map.put("Path", "/");

        // when
        Cookie cookie = new Cookie(cookieText);

        // then
        assertThat(cookie)
                .isEqualTo(new Cookie(map));
    }
}