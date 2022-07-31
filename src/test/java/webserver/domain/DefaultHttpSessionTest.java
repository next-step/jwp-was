package webserver.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DefaultHttpSessionTest {

    private SessionManager sessionManager;

    @BeforeEach
    void setUp() {
        sessionManager = new SessionManager();
    }

    @DisplayName("기본 생성자를 호출하면 UUID 기반의 아이디를 가진 세션이 생성된다.")
    @Test
    void createNoArgsSession() {
        DefaultHttpSession session = new DefaultHttpSession();

        assertThat(session.getId()).isNotNull();
    }

    @DisplayName("원하는 방식의 특정 아이디로 세션 객체를 만들 수 있다.")
    @Test
    void createWithParams() {
        DefaultHttpSession session = new DefaultHttpSession("test", Collections.singletonMap("key", "value"));

        assertAll(
                ()-> assertThat(session.getId()).isEqualTo("test"),
                ()-> assertThat(session.getAttribute("key")).isEqualTo("value")
        );
    }

    @DisplayName("정적 팩토리를 이용해 sessionManager를 구독하는 세션객체를 만들 수 있다.")
    @Test
    void createAndSubscribe() {
        HttpSession session = DefaultHttpSession.newInstance(sessionManager);

        assertThat(sessionManager.findBySessionId(session.getId())).isEqualTo(session);
    }

    @DisplayName("invalidate 메서드를 통해 세션정보를 초기화하고 세션 매니저와 연결을 해지할 수 있다.")
    @Test
    void invalidate() {
        HttpSession session = DefaultHttpSession.newInstance(sessionManager);

        session.invalidate();

        assertThat(sessionManager.findBySessionId(session.getId())).isNull();
    }

    @DisplayName("세션 정보에서 쿠키 정보를 반환할 수 있다.")
    @Test
    void getCookie() {
        HttpSession session = DefaultHttpSession.newInstance(sessionManager);

        Cookie cookie = session.getCookie();

        assertThat(session.getId()).isEqualTo(cookie.getValue());
    }

}
