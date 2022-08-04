package webserver.http.session;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpSessionTest {

    @DisplayName("생성 테스트")
    @Test
    void create() {
        UUID uuid = UUID.randomUUID();
        HttpSession httpSession = new HttpSession(uuid);

        assertThat(httpSession.getId()).isEqualTo(uuid.toString());
    }

        @DisplayName("세션 로그인 테스트")
        @Test
        void sessionLogin() {
            UUID uuid = UUID.randomUUID();
            HttpSession httpSession = new HttpSession(uuid);

            httpSession.setLogin(true);
            assertThat(httpSession.getLogin()).isTrue();

            httpSession.setLogin(false);
            assertThat(httpSession.getLogin()).isFalse();
        }
}