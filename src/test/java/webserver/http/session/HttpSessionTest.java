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
}