package webserver;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.*;

import java.util.Map;

class JwpSessionHandlerTest {

    @DisplayName("세션을 반환 할때, 요청에 세션 쿠키가 없으면, 세션스토어에서 세션을 생성 후 응답 세션 쿠키를 만든다.")
    @Test
    void notExistsSessionCookie() {
        // given
        HttpRequest httpRequest = new HttpRequest("POST /user/login HTTP/1.1");
        HttpResponse httpResponse = new HttpResponse();
        HttpSessionHandler httpSessionHandler = new JwpSessionHandler(new MemoryHttpSessionStore(() -> "NEW-SESSION"));

        // when
        httpSessionHandler.getHttpSession(httpRequest, httpResponse);

        // thee
        Assertions.assertThat(httpResponse.getHeaders().getValue("SET-COOKIE")).isEqualTo("JWP_SESSION=NEW-SESSION; Path=/");
    }


    @DisplayName("세션을 반환 할때, 요청에 세션 쿠키가 있으면, 세션스토어에 저장되어 있는 세션을 찾아 응답 세션 쿠키를 만든다.")
    @Test
    void existsSessionCookie() {
        // given
        Header requestCookieHeader = new Header("Cookie", "JWP_SESSION=OLD-SESSION");
        HttpRequest httpRequest = new HttpRequest(RequestLine.parseOf("POST /user/login HTTP/1.1"), Headers.of(requestCookieHeader));
        HttpResponse httpResponse = new HttpResponse();
        HttpSessionHandler httpSessionHandler = new JwpSessionHandler(new MemoryHttpSessionStore(Map.of("OLD-SESSION", new HttpSession("OLD-SESSION"))));

        // when
        httpSessionHandler.getHttpSession(httpRequest, httpResponse);

        // thee
        Assertions.assertThat(httpResponse.getHeaders().getValue("SET-COOKIE")).isEqualTo("JWP_SESSION=OLD-SESSION; Path=/");
    }

}
