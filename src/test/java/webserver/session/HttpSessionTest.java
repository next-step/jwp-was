package webserver.session;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpSessionTest {

    @DisplayName("세션을 생성한다")
    @Test
    void create_session() {
        final HttpSession actual = new HttpSession(() -> "12345");

        final HttpSession expected = new HttpSession(() -> "12345");

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("세션에 특정한 이름으로 객체를 저장할 수 있다")
    @Test
    void set_attribute() {
        final HttpSession firstSession = new HttpSession(() -> "12345");
        firstSession.setAttribute("name", "value");

        final HttpSession secondSession = new HttpSession(() -> "12345");
        secondSession.setAttribute("name", "value");

        assertThat(firstSession).isEqualTo(secondSession);
    }

    @DisplayName("세션에 저장된 객체를 조회할 수 있다")
    @Test
    void get_attribute() {
        final HttpSession session = new HttpSession(() -> "12345");
        session.setAttribute("name", "value");

        final Object actual = session.getAttribute("name");

        assertThat(actual).isEqualTo("value");
    }

    @DisplayName("세션에 저장된 객체가 없는 경우 null을 반환한다")
    @Test
    void get_attribute_when_not_exist() {
        final HttpSession session = new HttpSession(() -> "12345");

        final Object actual = session.getAttribute("name");

        assertThat(actual).isNull();
    }

    @DisplayName("세션에 저장된 객체를 삭제한다")
    @Test
    void remove_attribute() {
        final HttpSession session = new HttpSession(() -> "12345");
        session.setAttribute("name", "value");

        session.removeAttribute("name");

        final Object actual = session.getAttribute("name");

        assertThat(actual).isNull();
    }

    @DisplayName("세션에 저장된 모든 객체를 삭제한다")
    @Test
    void invalid_attribute() {
        final HttpSession actual = new HttpSession(() -> "12345");
        actual.setAttribute("name", "value");

        actual.invalidate();

        final HttpSession expected = new HttpSession(() -> "12345");

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("세션의 ID를 조회할 수 있다.")
    @Test
    void get_id() {
        final HttpSession httpSession = new HttpSession(() -> "12345");

        String actual = httpSession.getId();

        assertThat(actual).isEqualTo("12345");
    }

}
