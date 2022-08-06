package webserver.http.session;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpSessionTest {

    private UUID uuid;
    private HttpSession httpSession;

    @BeforeEach
    void setup() {
        uuid = UUID.randomUUID();
        httpSession = new HttpSession(uuid);
    }

    @DisplayName("생성 테스트")
    @Test
    void create() {
        assertThat(httpSession.getId()).isEqualTo(uuid.toString());
    }

    @DisplayName("세션 로그인 테스트")
    @Test
    void sessionLogin() {
        httpSession.setLogin(true);
        assertThat(httpSession.getLogin()).isTrue();

        httpSession.setLogin(false);
        assertThat(httpSession.getLogin()).isFalse();
    }

    @DisplayName("세션 attribute 삭제 테스트")
    @Test
    void removeTest() {
        httpSession.setLogin(true);
        httpSession.removeAttribute("isLogin");

        assertThat(httpSession.getAttribute("isLogin")).isNull();
    }

    @DisplayName("세션 모두 초기화 테스트")
    @Test
    void init() {
        httpSession.setLogin(true);
        httpSession.invalidate();

        assertThat(httpSession.getAttribute("isLogin")).isNull();
    }
}