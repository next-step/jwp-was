package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class HttpSessionTest {
    @Test
    @DisplayName("Http Session은 ")
    void httpSessionTest() {

    }

    @Test
    @DisplayName("현재 세션에 할당되어 있는 고유한 세션 아이디를 반환한다.")
    void getIdTest() {
        UUID uuid = UUID.randomUUID();
        HttpSession httpSession = new HttpSession(uuid);

        String result = httpSession.getId();

        assertThat(result).isEqualTo(uuid.toString());
    }

    @Test
    @DisplayName("현재 세션에 value 객체를 name 인자 이름으로 저장하고 가져올 수 있다.")
    void setAttributeTest() {
        UUID uuid = UUID.randomUUID();
        HttpSession httpSession = new HttpSession(uuid);

        Object sessionObject = UUID.randomUUID();

        httpSession.setAttribute("newName", sessionObject);
        Object result = httpSession.getAttribute("newName");

        assertThat(result).isEqualTo(sessionObject);
    }

    @Test
    @DisplayName("현재 세션에 name 인자로 저장되어 있는 객체 값을 삭제할 수 있다.")
    void removeAttributeTest() {
        UUID uuid = UUID.randomUUID();
        HttpSession httpSession = new HttpSession(uuid);
        Object sessionObject = UUID.randomUUID();

        httpSession.setAttribute("newName", sessionObject);
        httpSession.removeAttribute("newName");
        Object result = httpSession.getAttribute("newName");

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("현재 세션에 저장되어 있는 모든 값을 삭제한다.")
    void invalidateTest() {
        UUID uuid = UUID.randomUUID();
        HttpSession httpSession = new HttpSession(uuid);
        Object sessionObject = UUID.randomUUID();

        httpSession.setAttribute("first", sessionObject);
        httpSession.setAttribute("second", sessionObject);

        httpSession.invalidate();

        Object firstResult = httpSession.getAttribute("first");
        Object secondResult = httpSession.getAttribute("second");

        assertAll(
            () -> assertThat(firstResult).isNull(),
            () -> assertThat(secondResult).isNull()
        );
    }
}
