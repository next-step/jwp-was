package webserver.session;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionTest {
    HttpSession session;

    @Test
    @DisplayName("현재 세션에 value 인자로 전달되는 객체를 name 인자 이름으로 저장")
    void setSession() {
        makeSession();
        User abc = (User) session.getAttribute("abc");

        assertThat(abc).isEqualTo(new User("abc", "pass", "abc", "abc@naver.com"));
    }

    @Test
    @DisplayName("현재 세션에 name 인자로 저장되어 있는 객체 값을 삭제")
    void removeAttributeTest() {
        makeSession();
        session.removeAttribute("abc");
        assertThat(session.getAttribute("abc")).isNull();
    }

    @Test
    @DisplayName("현재 세션에 저장되어 있는 모든 값을 삭제")
    void invalidateTest() {
        makeSession();
        session.invalidate();
        assertThat(session.size()).isEqualTo(0);
    }

    private void makeSession() {
        session = new HttpSession();
        session.setAttribute("abc", new User("abc", "pass", "abc", "abc@naver.com"));
        session.setAttribute("cde", new User("cde", "pass", "cde", "cde@naver.com"));
    }
}
