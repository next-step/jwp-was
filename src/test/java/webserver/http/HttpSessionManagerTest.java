package webserver.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static webserver.http.HttpRequest.*;

@DisplayName("HttpSessionManager 단위 테스트")
class HttpSessionManagerTest {
    private HttpSession httpSession;

    @BeforeEach
    void setUp() {
        final HttpRequest httpRequest = new HttpRequest(new RequestLine("GET /users HTTP/1.1"), new Headers(), new Attributes());
        httpSession = HttpSessionManager.getHttpSession(httpRequest);
    }

    @DisplayName("세션이 존재하지 않으면 신규 세션을 생성한다.")
    @Test
    void getSession1() {
        assertThat(httpSession).isNotNull();
    }

    @DisplayName("세션이 존재하면 기존 세션을 반환한다.")
    @Test
    void getSession2() {
        // given
        final Headers headers = new Headers();
        headers.setCookie(SESSION_ID_KEY, httpSession.getId());
        final HttpRequest httpRequest = new HttpRequest(new RequestLine("GET /users HTTP/1.1"), headers, new Attributes());

        // when
        final HttpSession savedHttpSession = HttpSessionManager.getHttpSession(httpRequest);

        // then
        assertThat(savedHttpSession).isEqualTo(httpSession);
    }
}
