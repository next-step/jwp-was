package webserver.session;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class HttpSessionContextTest {

    @DisplayName("세션 저장소에 세션을 저장 후 조회한다")
    @Test
    void add_session() {
        final HttpSession session = new HttpSession(() -> "12345");

        HttpSessionContext.add(session);

        final HttpSession actual = HttpSessionContext.get(session.getId());

        assertThat(actual).isEqualTo(new HttpSession(() -> "12345"));
    }

    @DisplayName("null을 조회하면 null을 반환한다")
    @Test
    void get_null_session_() {
        //given
        final HttpSession httpSession = HttpSessionContext.get(null);

        Assertions.assertThat(httpSession).isNull();
    }

    @DisplayName("세션 저장 여부를 확인한다")
    @ParameterizedTest
    @CsvSource({
        "12345, true",
        "ABCDEFG, false"
    })
    void exists_session(String sessionId, boolean expected) {
        final HttpSession session = new HttpSession(() -> "12345");

        HttpSessionContext.add(session);

        final boolean actual = HttpSessionContext.has(sessionId);

        assertThat(actual).isEqualTo(expected);
    }

}
