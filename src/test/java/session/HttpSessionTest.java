package session;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpSessionTest {

    @Test
    @DisplayName("세션에 속성값을 추가합니다.")
    void setAttributeTest() {
        HttpSession session = HttpSession.from(SessionAttribute.empty());
        session.setAttribute("role", "ROLE_USER");

        assertThat(session.getAttribute("role")).isEqualTo("ROLE_USER");
    }

    @Test
    @DisplayName("세션의 속성값을 제거합니다.")
    void removeAttributeTest() {
        HttpSession session = HttpSession.from(SessionAttribute.empty());
        session.setAttribute("role", "ROLE_USER");
        session.removeAttribute("role");

        assertThat(session.getAttribute("role")).isNull();
    }

    @Test
    @DisplayName("세션의 모든 값을 초기화 합니다.")
    void invalidateTest() {
        HttpSession session = HttpSession.from(SessionAttribute.empty());
        session.setAttribute("role", "ROLE_USER");
        session.setAttribute("nextstep", "was만들어보기");

        session.invalidate();

        HttpSession newSession = new HttpSession(session.getId(), SessionAttribute.empty());

        assertThat(session).isEqualTo(newSession);
    }

}
