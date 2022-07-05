package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class HttpSessionTest {

    @DisplayName("세션을 생성할수 있다.")
    @Test
    void create() {
        assertThatCode(() -> new HttpSession(UUID.randomUUID().toString())).doesNotThrowAnyException();
    }

    @DisplayName("세션 속성을 저장할수 있다.")
    @Test
    void setAttribute() {
        HttpSession httpSession = new HttpSession(UUID.randomUUID().toString());
        httpSession.setAttribute("login", true);

        assertThat(httpSession.getAttribute("login")).isEqualTo(true);
    }

    @DisplayName("세션 속성을 제거할수 있다.")
    @Test
    void removeAttribute() {
        HttpSession httpSession = new HttpSession(UUID.randomUUID().toString());
        httpSession.setAttribute("login", true);

        httpSession.removeAttribute("login");

        assertThat(httpSession.getAttribute("login")).isNull();
    }

    @DisplayName("세션에 저장된 모든 값을 삭제할수 있다.")
    @Test
    void invalidateAttribute() {
        HttpSession httpSession = new HttpSession(UUID.randomUUID().toString());
        httpSession.setAttribute("login", true);
        httpSession.setAttribute("name", "dean");

        httpSession.invalidate();

        assertThat(httpSession.getAttribute("login")).isNull();
        assertThat(httpSession.getAttribute("name")).isNull();
    }
}
