package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("HttpCookie 테스트")
class HttpCookieTest {

    @DisplayName("쿠키 생성")
    @Test
    void create() {
        HttpCookie cookie = HttpCookie.of("NEXTSTEP_REMEMBER_ME=eavadcad; JSESSIONID=jaeyong123Session");
        String cookie1 = cookie.cookieList();
        assertThat(cookie1).isEqualTo("NEXTSTEP_REMEMBER_ME=eavadcad; JSESSIONID=jaeyong123Session");
    }

}
