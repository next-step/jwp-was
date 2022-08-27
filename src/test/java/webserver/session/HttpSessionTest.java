package webserver.session;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class HttpSessionTest {

    @Test
    @DisplayName("getAttribute 메소드 테스트")
    public void getAttributeTest() {
        UUID uuid = UUID.randomUUID();
        HttpSession session = HttpSession.createSession(String.valueOf(uuid));

        session.setAttribute("kim", 12345);

        Assertions.assertThat(session.getAttribute("kim")).isEqualTo(12345);
    }

    @Test
    @DisplayName("없는 속성을 get 또는 제거하려고 하면 예외를 던진다.")
    public void removeAttributeTest() {
        UUID uuid = UUID.randomUUID();
        HttpSession session = HttpSession.createSession(String.valueOf(uuid));

        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
           session.getAttribute("kim");
        });

        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            session.removeAttribute("kim");
        });
    }

    @Test
    @DisplayName("invalidate 테스트")
    public void invalidateTest() {
        UUID uuid = UUID.randomUUID();
        HttpSession session = HttpSession.createSession(String.valueOf(uuid));

        session.setAttribute("kim1", 12345);
        session.setAttribute("kim2", 54321);
        session.setAttribute("kim3", 321);

        session.invalidate();

        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            session.removeAttribute("kim1");
        });

        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            session.removeAttribute("kim2");
        });

        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            session.removeAttribute("kim3");
        });
    }
}