package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseTest {

    @DisplayName("응답 객체에 쿠키를 추가할 수 있다.")
    @Test
    void addCookieTest() {
        // given
        Cookie loginCookie = new Cookie("logined", "true", "/");
        HttpResponse httpResponse = new HttpResponse();

        // when
        httpResponse.addCookie(loginCookie);

        // then
        assertThat(httpResponse.getHeaders().getValue("Set-Cookie")).isEqualTo("logined=true; Path=/");
    }

}
