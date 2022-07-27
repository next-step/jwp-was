package webserver.http;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpSessionTest {

    @DisplayName("세션의 id를 조회한다.")
    @Test
    void getId() {
        String id = UUID.randomUUID().toString();
        HttpSession session = new HttpSession(id);
        assertThat(session.getId()).isEqualTo(id);
    }

    @DisplayName("세션에 데이터를 추가하거나, 세션에서 데이터를 조회할 수 있다.")
    @Test
    void attribute() {
        String id = UUID.randomUUID().toString();
        HttpSession session = new HttpSession(id);
        session.setAttribute("name", "홍길동");
        assertThat(session.getAttribute("name")).isEqualTo("홍길동");
    }
}
