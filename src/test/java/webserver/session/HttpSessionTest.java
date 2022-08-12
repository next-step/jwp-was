package webserver.session;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Http 세션 테스트")
class HttpSessionTest {

    @DisplayName("세션 생성")
    @Test
    void create() {
        assertThatNoException().isThrownBy(() -> new HttpSession(HttpSessionStorage.generateRandomId()));
    }

    @DisplayName("세션 아이디 반환 테스트")
    @Test
    void returnSessionId() {
        String randomId = HttpSessionStorage.generateRandomId();
        HttpSession session = new HttpSession(randomId);

        assertThat(session.getId()).isEqualTo(randomId);
    }

    @DisplayName("속성값 추가")
    @Test
    void setAttribute() {
        HttpSession session = new HttpSession(HttpSessionStorage.generateRandomId());

        session.setAttribute("key", "value");

        assertThat(session.getAttribute("key")).isEqualTo("value");
    }

    @DisplayName("속성은 key-value 형태여야만 한다.")
    @Test
    void nullPairIllegalArgumentException() {
        HttpSession session = new HttpSession(HttpSessionStorage.generateRandomId());

        assertAll(
                () -> assertThatIllegalArgumentException().isThrownBy(
                        () -> session.setAttribute(null, "value")),
                () -> assertThatIllegalArgumentException().isThrownBy(
                        () -> session.setAttribute("key", null)),
                () -> assertThatIllegalArgumentException().isThrownBy(
                        () -> session.setAttribute(null, null))
        );
    }

    @DisplayName("속성값 삭제")
    @Test
    void remove() {
        HttpSession session = new HttpSession(HttpSessionStorage.generateRandomId());
        session.setAttribute("key", "value");

        session.removeAttribute("key");

        assertThat(session.contains("key")).isFalse();
    }

    @DisplayName("모든 속성값 한번에 삭제")
    @Test
    void invalidate() {
        HttpSession session = new HttpSession(HttpSessionStorage.generateRandomId());
        session.setAttribute("key1", "value1");
        session.setAttribute("key2", "value2");

        session.invalidate();

        assertAll(
                () -> assertThat(session.contains("key1")).isFalse(),
                () -> assertThat(session.contains("key2")).isFalse()
        );
    }
}
