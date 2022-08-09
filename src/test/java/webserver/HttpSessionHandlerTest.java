package webserver;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.*;

import java.util.Map;

class HttpSessionHandlerTest {

    @DisplayName("요청에 세션 쿠키가 없으면, 세션스토어에서 세션을 생성해서 응답 세션 쿠키를 만든다.")
    @Test
    void notExistsSessionCookie() {
        // given
        HttpRequest httpRequest = new HttpRequest("POST /user/login HTTP/1.1");
        HttpResponse httpResponse = new HttpResponse();
        HttpSessionStore mockSessionStore = new MemoryHttpSessionStore(() -> "NEW-SESSION");
        HttpSessionHandler sessionInitializer = new HttpSessionHandler(mockSessionStore);

        // when
        sessionInitializer.handleSession(httpRequest, httpResponse);

        // thee
        Assertions.assertThat(httpResponse.getHeaders().getValue("SET-COOKIE")).isEqualTo("JWP_SESSION=NEW-SESSION; Path=/");
    }


    @DisplayName("요청에 세션 쿠키가 있으면, 세션스토어에 저장되어 있는 세션을 찾아 응답 세션 쿠키를 만든다.")
    @Test
    void existsSessionCookie() {
        // given
        Header requestCookieHeader = new Header("Cookie", "JWP_SESSION=OLD-SESSION");
        HttpRequest httpRequest = new HttpRequest(RequestLine.parseOf("POST /user/login HTTP/1.1"), Headers.of(requestCookieHeader));
        HttpResponse httpResponse = new HttpResponse();
        HttpSessionStore mockSessionStore = new MemoryHttpSessionStore(
                Map.of("OLD-SESSION", new HttpSession("OLD-SESSION"))
        );
        HttpSessionHandler sessionInitializer = new HttpSessionHandler(mockSessionStore);

        // when
        sessionInitializer.handleSession(httpRequest, httpResponse);

        // thee
        Assertions.assertThat(httpResponse.getHeaders().getValue("SET-COOKIE")).isEqualTo("JWP_SESSION=OLD-SESSION; Path=/");
    }

}
