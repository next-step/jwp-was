package webserver.domain.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleSessionTest {

    @DisplayName("랜덤 id를 생성할 수 있다")
    @Test
    void getId() {
        SimpleSession session = new SimpleSession();

        String actual = session.getId();

        assertThat(actual).isNotNull();
    }

    @DisplayName("속성을 추가 할 수 있다")
    @Test
    void setAttribute() {
        SimpleSession session = new SimpleSession();

        session.setAttribute("user", "test");

        Object actual = session.getAttribute("user");
        assertThat(actual).isEqualTo("test");
    }

    @DisplayName("속성을 제거할 수 있다")
    @Test
    void removeAttribute() {
        SimpleSession session = new SimpleSession();
        session.setAttribute("user", "test");

        session.removeAttribute("user");

        Object actual = session.getAttribute("user");
        assertThat(actual).isNull();
    }

    @DisplayName("현재 세션에 저장되어 있는 모든 값을 삭제 할 수 있다")
    @Test
    void invalidate() {
        SimpleSession session = new SimpleSession();
        session.setAttribute("user", "test");
        session.setAttribute("user2", "test2");

        session.invalidate();

        Object actual = session.getAttribute("user");
        Object actual2 = session.getAttribute("user2");
        assertThat(actual).isNull();
        assertThat(actual2).isNull();
    }
}
