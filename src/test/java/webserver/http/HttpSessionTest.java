package webserver.http;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpSessionTest {

    private static final String SESSION_ID = UUID.randomUUID().toString();

    private HttpSession session;

    @BeforeEach
    void setup() {
        session = new HttpSession(SESSION_ID);
    }

    @DisplayName("세션의 id를 조회한다.")
    @Test
    void getId() {
        assertThat(session.getId()).isEqualTo(SESSION_ID);
    }

    @DisplayName("세션에 데이터를 추가하거나, 세션에서 데이터를 조회할 수 있다.")
    @Test
    void attribute() {
        session.setAttribute("name", "홍길동");
        assertThat(session.getAttribute("name")).isEqualTo("홍길동");
    }

    @DisplayName("세션의 데이터를 삭제할 수 있다.")
    @Test
    void removeAttribute() {
        session.setAttribute("name", "홍길동");
        session.removeAttribute("name");
        assertThat(session.getAttribute("name")).isNull();
    }

    @DisplayName("세션의 모든 데이터를 삭제할 수 있다.")
    @Test
    void invalidate() {
        session.setAttribute("name", "홍길동");
        session.setAttribute("age", 20);
        session.invalidate();
        assertThat(session.getAttribute("name")).isNull();
        assertThat(session.getAttribute("age")).isNull();
    }
}
