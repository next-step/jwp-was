package webserver.session;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpSessionTest {
    @DisplayName("HttpSession 객체 생성 시, id 자동 부여")
    @Test
    void setIdAutomaticallyWhenCreate() {
        //when
        HttpSession httpSession = new HttpSession();

        //then
        assertThat(httpSession.getId()).isNotNull();
    }

    @DisplayName("Session에 Value 객체 저장")
    @Test
    void setAttribute() {
        //given
        HttpSession session = new HttpSession();

        //when
        session.setAttribute("isAdmin", true);

        //then
        assertThat((boolean) session.getAttribute("isAdmin")).isTrue();
    }
}
