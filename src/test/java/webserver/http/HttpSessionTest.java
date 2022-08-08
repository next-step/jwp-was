package webserver.http;

import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpSessionTest {

    @DisplayName("Session 은 id 와 함께 생성할 수 있다.")
    @Test
    void getIdTest() {
        // given
        HttpSession httpSession = new HttpSession("test");

        // when
        String id = httpSession.getId();

        // then
        Assertions.assertThat(id).isEqualTo("test");
    }

    @DisplayName("Session 에 데이터를 저장하고 조회할 수 있다.")
    @Test
    void setAttributeTest() {
        // given
        HttpSession httpSession = new HttpSession("test");

        // when
        httpSession.setAttribute("logined", true);

        // then
        Assertions.assertThat(httpSession.getAttribute("logined")).isEqualTo(true);
    }

    @DisplayName("Session 에 데이터를 삭제할 수 있다.")
    @Test
    void removeAttribute() {
        // given
        HttpSession httpSession = new HttpSession("test");
        httpSession.setAttribute("logined", true);

        // when
        httpSession.removeAttribute("logined");

        // then
        Assertions.assertThat(httpSession.getAttribute("logined")).isNull();
    }

    @DisplayName("Session 에 저장되어 있는 모든 값을 삭제할 수 있다.")
    @Test
    void invalidateTest() {
        // given
        HttpSession httpSession = new HttpSession("test");
        httpSession.setAttribute("logined", true);
        httpSession.setAttribute("currentUser", new User("", "", "", ""));

        // when
        httpSession.invalidate();

        // then
        Assertions.assertThat(httpSession.getAttribute("logined")).isNull();
        Assertions.assertThat(httpSession.getAttribute("currentUser")).isNull();
    }

}
