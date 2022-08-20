package webserver.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("HttpSessions 테스트")
public class HttpSesionsTest {
    private static final String ID = "jdragon-id";
    private static final String NAME = "jdragon";
    private HttpSession httpSession;

    @BeforeEach
    void setUp() {
        HttpSessions.remove(ID);
    }

    @DisplayName("저장된 세션이 없는 경우 세션을 저장하고 조회한다.")
    @Test
    void save() {
        httpSession = HttpSessions.getSession(ID);
        httpSession.setAttribute("name", NAME);

        assertThat(httpSession.getId()).isEqualTo(ID);
        assertThat(httpSession.getAttribute("name")).isEqualTo(NAME);
    }

    @DisplayName("저장된 세션이 있는 경우 세션을 조회한다.")
    @Test
    void alreadySaveThenGetSession() {
        save();

        HttpSession getSession = HttpSessions.getSession(ID);
        assertThat(getSession.getId()).isEqualTo(ID);
        assertThat(getSession.getAttribute("name")).isEqualTo(NAME);
    }

    @DisplayName("세션 삭제")
    @Test
    void remove() {
        save();
        httpSession.invalidate();

        HttpSession session = HttpSessions.getSession(ID);
        assertThat(session.getAttribute("name")).isNull();
    }
}
