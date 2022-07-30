package webserver.session;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class HttpSessionIdHolderTest {

    private final String newSessionId = "12345";

    @DisplayName("세션 ID를 저장한다")
    @Test
    void generate_session() {
        HttpSessionIdHolder.generate(newSessionId);

        final String actual = HttpSessionIdHolder.getSessionId();

        assertThat(actual).isEqualTo(newSessionId);
    }

    @DisplayName("Holder의 세션 ID를 삭제한다")
    @Test
    void delete_session_id__holder() {
        HttpSessionIdHolder.generate(newSessionId);

        HttpSessionIdHolder.invalidate();

        assertThat(HttpSessionIdHolder.getSessionId()).isNull();
    }
}
