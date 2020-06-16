package http.request;

import http.request.exception.HttpHeaderRegistrationException;
import http.session.HttpSession;
import http.session.HttpSessionMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static http.session.HttpSession.SESSION_HEADER_KEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpRequestTest {

    @DisplayName("HTTP header 등록하기")
    @Test
    void registerHeader() {
        /* given */
        HttpRequest httpRequest = new HttpRequest("GET /users HTTP/1.1");
        String headerLine = "Host: localhost:8080";

        /* when */
        httpRequest.registerHeader(headerLine);

        /* then */
        assertThat(httpRequest.getHeader("Host")).isEqualTo("localhost:8080");
    }

    @DisplayName("등록하려는 header line이 구분자 ':'를 가지고 있지 않다면 Exception")
    @Test
    void registerException() {
        /* given */
        HttpRequest httpRequest = new HttpRequest("GET /users HTTP/1.1");
        String headerLine = "Connection keep-alive";

        /* when */ /* then */
        assertThrows(HttpHeaderRegistrationException.class, () -> httpRequest.registerHeader(headerLine));
    }

    @DisplayName("SessionId를 가지고 있다면 기존 Session 반환")
    @Test
    void getSession() {
        /* given */
        HttpSession existingSession = HttpSessionMap.createSession();
        String sessionId = existingSession.getId();

        HttpRequest httpRequest = new HttpRequest("GET /users HTTP/1.1");

        String headerLine = String.format("Cookie: %s=%s", SESSION_HEADER_KEY, sessionId);
        httpRequest.registerHeader(headerLine);

        /* when */
        HttpSession httpSession = httpRequest.getSession();

        /* then */
        assertThat(httpSession.getId()).isEqualTo(sessionId);
    }

    @DisplayName("SessionId가 없다면 새로운 Session 생성 후 반환")
    @Test
    void getNewSession() {
        /* given */
        HttpRequest httpRequest = new HttpRequest("GET /users HTTP/1.1");

        /* when */
        HttpSession httpSession = httpRequest.getSession();

        /* then */
        assertThat(httpSession).isNotNull();
    }

    @DisplayName("같은 HttpRequest에서 Session을 두 번 가져오더라도 같은 Session이다.")
    @Test
    void getSessionTwice() {
        /* given */
        HttpRequest httpRequest = new HttpRequest("GET /users HTTP/1.1");

        /* when */
        HttpSession httpSession1 = httpRequest.getSession();
        HttpSession httpSession2 = httpRequest.getSession();

        /* then */
        assertThat(httpSession1).isEqualTo(httpSession2);
    }
}
