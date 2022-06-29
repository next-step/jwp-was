package webserver.session;

import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class HttpSessionImplTest {

    private HttpSession httpSession;

    @BeforeEach
    void setUp() {
        httpSession = new HttpSessionImpl();
    }


    @DisplayName("세션에 객체를 등록하고, name 을 통해 조회할 수 있다.")
    @Test
    void setAttribute() {
        httpSession.setAttribute("name1", "value1");
        httpSession.setAttribute("name2", "value2");

        assertAll(
                () -> assertThat(httpSession.getAttribute("name1")).isEqualTo("value1"),
                () -> assertThat(httpSession.getAttribute("name2")).isEqualTo("value2")
        );
    }

    @DisplayName("세션에 name 인자로 저장되어 있는 객체 값을 삭제할 수 있다.")
    @Test
    void removeAttribute() {
        httpSession.setAttribute("name1", "value1");
        httpSession.setAttribute("name2", "value2");
        httpSession.removeAttribute("name2");

        assertAll(
                () -> assertThat(httpSession.getAttribute("name1")).isEqualTo("value1"),
                () -> assertThat(httpSession.getAttribute("name2")).isNull()
        );
    }

    @DisplayName("세션에 저장되어 있는 모든 값을 삭제할 수 있다.")
    @Test
    void invalidate() {
        httpSession.setAttribute("name1", "value1");
        httpSession.setAttribute("name2", "value2");
        httpSession.invalidate();

        assertAll(
                () -> assertThat(httpSession.getAttribute("name1")).isNull(),
                () -> assertThat(httpSession.getAttribute("name2")).isNull()
        );
    }
}
