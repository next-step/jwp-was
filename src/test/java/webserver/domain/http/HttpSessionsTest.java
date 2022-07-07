package webserver.domain.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionsTest {

    @DisplayName("새로운 세션을 생성 할 수 있다")
    @Test
    void create() {
        HttpSession session = HttpSessions.create();

        HttpSession actual = HttpSessions.get(session.getId());

        assertThat(actual).isNotNull();
    }

    @DisplayName("조회 시 세션이 존재 하지 않으면 신규 세션을 생성 할 수 있다")
    @Test
    void getNewSession() {
        String id = "123";

        HttpSession actual = HttpSessions.get(id);

        assertThat(actual).isNotNull();
    }
}
