package webserver.session;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpSessionContextTest {

    @DisplayName("세션 저장소에 세션을 저장 후 조회한다")
    @Test
    void add_session() {
        final HttpSession session = new HttpSession(() -> "12345");

        HttpSessionContext.add(session);

        final HttpSession actual = HttpSessionContext.get(session.getId());

        assertThat(actual).isEqualTo(new HttpSession(() -> "12345"));
    }

}
