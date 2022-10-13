package webserver.http.session;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("HttpSession 테스트")
class HttpSessionTest {

    @Test
    @DisplayName("HttpSession 객체를 생성한다.")
    void create() {
        SessionId sessionId = SessionId.generate();
        HttpSession actual = new HttpSession(sessionId);

        assertThat(actual.getId()).isNotNull();
    }
}
