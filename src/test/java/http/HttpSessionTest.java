package http;


import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.junit.jupiter.api.Assertions.assertAll;

class HttpSessionTest {

    @Test
    @DisplayName("생성")
    void create() {

        HttpSession httpSession = new HttpSession("189a3eb0-ae53-4f77-bf1c-8f0e01bce0fb", new SessionAttribute(Map.of("user", "user")));

        assertAll(
            () -> assertThat(httpSession.getId()).isEqualTo("189a3eb0-ae53-4f77-bf1c-8f0e01bce0fb"),
            () -> assertThat(httpSession.getAttribute("user")).isEqualTo("user")
        );

    }

    @Test
    @DisplayName("속성 추가")
    void setAttribute() {
        //given
        HttpSession session = HttpSession.empty();
        //when
        session.setAttribute("key", "value");
        //then
        assertThat(session.getAttribute("key")).isEqualTo("value");
    }

    @Test
    @DisplayName("속성 키와 값은 필수")
    void setAttributeMustNotNull() {
        //given
        HttpSession session = HttpSession.empty();
        //when, then
        assertAll(
                () -> assertThatNullPointerException().isThrownBy(() -> session.setAttribute(null, "value")),
                () -> assertThatNullPointerException().isThrownBy(() -> session.setAttribute("key", null))
        );
    }

    @Test
    @DisplayName("저장된 속성 지우기")
    void removeAttribute() {
        //given
        HttpSession session = HttpSession.empty();
        session.setAttribute("key", "value");
        //when
        session.removeAttribute("key");
        //then
        assertThat(session.doesNotContainAttribute("key")).isTrue();
    }

    @Test
    @DisplayName("모든 값 삭제")
    void invalidate() {
        //given
        HttpSession session = HttpSession.empty();
        session.setAttribute("key", "value");
        session.setAttribute("key2", "value3");
        //when
        session.invalidate();
        //then
        assertAll(
                () -> assertThat(session.doesNotContainAttribute("key")).isTrue(),
                () -> assertThat(session.doesNotContainAttribute("key2")).isTrue()
        );
    }

}