package webserver.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("HttpSession 테스트")
class HttpSessionTest {

    private HttpSession session;

    @BeforeEach
    void setUp() {
        session = HttpSession.of("id");
    }

    @DisplayName("세션 생성")
    @Test
    void create() {
        assertThat(session.getId()).isEqualTo("id");
    }

    @DisplayName("attribute 추가")
    @Test
    void setAttribute() {
        String name = "jdragon";
        session.setAttribute("name", name);
        assertThat(session.getAttribute("name")).isEqualTo(name);
    }

    @DisplayName("attribute 삭제")
    @Test
    void removeAttribute() {
        setAttribute();
        session.removeAttribute("name");
        assertThat(session.getAttribute("name")).isNull();
    }

    @Test
    void tet() {

    }

}
