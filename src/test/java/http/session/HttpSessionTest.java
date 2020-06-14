package http.session;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionTest {
    private HttpSession httpSession;

    @BeforeEach
    void setUp() {
        httpSession = HttpSession.getInstance("test");
    }

    @Test
    @DisplayName("객체 생성 테스트")
    void createByConstructor() {
        // give
        HttpSession actual = httpSession;
        HttpSession expected = HttpSession.getInstance("test");
        // when
        boolean same = actual.equals(expected);
        // then
        assertThat(same).isTrue();
    }

    @Test
    @DisplayName("값 가져오기 테스트")
    void getAttribute() {
        // give
        httpSession.setAttribute("email", "liquidjoo@gmail.com");
        String actual = (String) httpSession.getAttribute("email");
        String expected = "liquidjoo@gmail.com";
        // when
        boolean same = actual.equals(expected);
        // then
        assertThat(same).isTrue();
    }

    @Test
    @DisplayName("값 삭제")
    void removeAttribute() {
        // give
        httpSession.setAttribute("email", "liquidjoo@gmail.com");
        httpSession.removeAttribute("email");
        HttpSession actual = httpSession;
        HttpSession expected = HttpSession.getInstance("test");
        // when
        boolean same = actual.equals(expected);
        // then
        assertThat(same).isTrue();
    }

    @Test
    @DisplayName("초기화")
    void invalidate() {
        // give
        httpSession.setAttribute("email", "liquidjoo@gmail.com");
        httpSession.setAttribute("name", "seongju");
        httpSession.setAttribute("mac", true);

        httpSession.invalidate();
        HttpSession actual = httpSession;
        HttpSession expected = HttpSession.getInstance("test");
        // when
        boolean same = actual.equals(expected);
        // then
        assertThat(same).isTrue();
    }

    @Test
    @DisplayName("세션 아이디 가져오기")
    void getSessionId() {
        // give
        String actual = httpSession.getId();
        String expected = "test";
        // when
        boolean same = actual.equals(expected);
        // then
        assertThat(same).isTrue();
    }
}
