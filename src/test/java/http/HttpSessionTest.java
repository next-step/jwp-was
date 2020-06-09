package http;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class HttpSessionTest {

    @Test
    void get_set_attribute() {
        HttpSession httpSession = new HttpSession("temp");
        httpSession.setAttribute("name", "홍길동");

        assertThat(httpSession.getAttribute("name")).isEqualTo("홍길동");
    }

    @Test
    void removeAttribute() {
        HttpSession httpSession = new HttpSession("temp");
        httpSession.setAttribute("name", "홍길동");
        httpSession.setAttribute("age", 20);

        httpSession.removeAttribute("name");

        assertThat(httpSession.getAttribute("name")).isNull();
        assertThat(httpSession.getAttribute("age")).isEqualTo(20);
    }

    @Test
    void invalidate() {
        HttpSession httpSession = new HttpSession("temp");
        httpSession.setAttribute("name", "홍길동");
        httpSession.setAttribute("age", 20);

        httpSession.invalidate();

        assertThat(httpSession).isEqualTo(new HttpSession("temp"));
    }
}
