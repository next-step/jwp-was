package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("HttpSession 단위 테스트")
public class HttpSessionTest {
    @DisplayName("고유한 세션 아이디를 반환한다.")
    @Test
    void getId() {
        // when
        HttpSession httpSession1 = new HttpSession();
        HttpSession httpSession2 = new HttpSession();

        // then
        assertAll(
                () -> assertThat(httpSession1.getId()).isNotBlank(),
                () -> assertThat(httpSession2.getId()).isNotBlank(),
                () -> assertThat(httpSession1.getId()).isNotEqualTo(httpSession2.getId())
        );
    }

    @DisplayName("속성을 저장한다.")
    @Test
    void setAttribute() {
        // given
        HttpSession httpSession = new HttpSession();

        // when
        httpSession.setAttribute("name", "value");

        // then
        assertThat(httpSession.getAttribute("name")).isEqualTo("value");
    }

    @DisplayName("속성을 삭제한다.")
    @Test
    void removeAttribute() {
        // given
        HttpSession httpSession = new HttpSession();
        httpSession.setAttribute("name", "value");

        // when
        httpSession.removeAttribute("name");

        // then
        assertThat(httpSession.getAttribute("name")).isNull();
    }
}
