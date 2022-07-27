package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseTest {

    @DisplayName("응답 객체에 쿠키를 추가할 수 있다.")
    @Test
    void addCookieTest() {
        // given
        Cookie loginCookie = new Cookie("logined", "true", "/");
        Response response = new Response();

        // when
        response.addCookie(loginCookie);

        // then
        assertThat(response.getHeaders().getValue("Set-Cookie")).isEqualTo("logined=true; Path=/");
    }

}