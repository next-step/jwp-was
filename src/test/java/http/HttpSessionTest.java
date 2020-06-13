package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.MapEntry.entry;

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

        Map<String, String> attributes = httpSession.getAttributes();
        assertThat(attributes).hasSize(1);
        assertThat(attributes).contains(entry("a", "b"));
    }

    @DisplayName("name으로 value를 확인한다.")
    @Test
    void getAttribute() {
        HttpSession httpSession = new HttpSession();
        httpSession.setAttribute("a", "b");

        String value = httpSession.getAttribute("a");
        assertThat(value).isEqualTo("b");
    }

    @DisplayName("name으로 attribute를 삭제한다.")
    @Test
    void removeAttribute() {
        HttpSession httpSession = new HttpSession();
        httpSession.setAttribute("a", "b");
        httpSession.setAttribute("c", "d");

        httpSession.removeAttribute("c");

        Map<String, String> attributes = httpSession.getAttributes();
        assertThat(attributes).hasSize(1);
        assertThat(attributes).contains(entry("a", "b"));
    }

    @DisplayName("저장되어 있는 값을 모두 삭제한다.")
    @Test
    void invalidate() {
        HttpSession httpSession = new HttpSession();
        httpSession.setAttribute("a", "b");
        httpSession.setAttribute("c", "d");

        httpSession.invalidate();

        assertThat(httpSession.getAttributes()).hasSize(0);
    }
}
