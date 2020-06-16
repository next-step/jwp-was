package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionTest {

    @DisplayName("HttpSession을 생성하면 UUID가 생성된다.")
    @Test
    void createHttpSession() {
        HttpSession httpSession = new HttpSession();
        assertThat(httpSession.getId()).hasSize(36);
    }

    @DisplayName("setAttribute하면 name과 value가 셋된다.")
    @Test
    void setAttribute() {
        HttpSession httpSession = new HttpSession();
        httpSession.setAttribute("a", "b");

        assertThat(httpSession.getAttribute("a")).isEqualTo("b");
    }

    @DisplayName("name으로 attribute를 삭제한다.")
    @Test
    void removeAttribute() {
        HttpSession httpSession = new HttpSession();
        httpSession.setAttribute("a", "b");
        httpSession.setAttribute("c", "d");

        httpSession.removeAttribute("c");

        assertThat(httpSession.getAttribute("a")).isEqualTo("b");
        assertThat(httpSession.getAttribute("c")).isEqualTo(null);
    }

    @DisplayName("저장되어 있는 값을 모두 삭제한다.")
    @Test
    void invalidate() {
        HttpSession httpSession = new HttpSession();
        httpSession.setAttribute("a", "b");
        httpSession.setAttribute("c", "d");

        httpSession.invalidate();

        assertThat(httpSession.getAttribute("a")).isEqualTo(null);
        assertThat(httpSession.getAttribute("c")).isEqualTo(null);
    }
}
