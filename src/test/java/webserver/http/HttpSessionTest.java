package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Collections;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("http 세션")
class HttpSessionTest {

    @Test
    @DisplayName("생성")
    void instance() {
        assertAll(
                () -> assertThatNoException().isThrownBy(() -> HttpSession.of("id", Collections.emptyMap())),
                () -> assertThatNoException().isThrownBy(HttpSession::empty)
        );
    }

    @Test
    @DisplayName("속성 추가")
    void setAttribute() {
        //given
        Session session = HttpSession.empty();
        //when
        session.setAttribute("key", "value");
        //then
        assertThat(session.getAttribute("key")).isEqualTo("value");
    }

    @Test
    @DisplayName("속성 키와 값은 필수")
    void setAttribute_null_thrownIllegalArgumentException() {
        //given
        Session session = HttpSession.empty();
        //when, then
        assertAll(
                () -> assertThatIllegalArgumentException().isThrownBy(() -> session.setAttribute(null, "value")),
                () -> assertThatIllegalArgumentException().isThrownBy(() -> session.setAttribute("key", null))
        );
    }

    @Test
    @DisplayName("저장된 속성 지우기")
    void removeAttribute() {
        //given
        Session session = HttpSession.empty();
        session.setAttribute("key", "value");
        //when
        session.removeAttribute("key");
        //then
        assertThat(session.containsAttribute("key")).isFalse();
    }

    @Test
    @DisplayName("모든 값 삭제")
    void invalidate() {
        //given
        Session session = HttpSession.empty();
        session.setAttribute("key", "value");
        //when
        session.invalidate();
        //then
        assertThat(session.containsAttribute("key")).isFalse();
    }

    @Test
    @DisplayName("세션에 접근하면 마지막 접근 시간 변경")
    void access() {
        //given
        Session session = HttpSession.empty();
        Instant beforeLastAccessedAt = session.lastAccessedAt();
        //when
        session.access();
        //then
        assertThat(session.lastAccessedAt()).isAfter(beforeLastAccessedAt);
    }
}