package http.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HttpSessionTest {

    private String sessionId;
    private HttpSession httpSessionStub;

    @BeforeEach
    void setUp() {
        sessionId = "870be194-0ef2-4dae-99c7-227b6811238c";
        httpSessionStub = new HttpSession(sessionId);
    }

    @DisplayName("세션에 할당된 아이디 반환 ")
    @Test
    void test_getId() {
        // given
        // when
        String id = httpSessionStub.getId();
        // then
        assertThat(id.equals(sessionId));
    }

    @DisplayName("세션에 key로 값을 저장하고 이 값을 key로 찾아 반환")
    @Test
    void test_setAttribute_getAttribute() {
        // given
        Object original = new Object();
        // when
        httpSessionStub.setAttribute("key", original);
        // then
        Object result = httpSessionStub.getAttribute("key");
        assertThat(result.equals(original));
    }

    @DisplayName("세션에 key로 저장된 값 삭제")
    @Test
    void test_removeAttribute() {
        // given
        Object original = new Object();
        httpSessionStub.setAttribute("key", original);
        // when
        httpSessionStub.removeAttribute("key");
        // then
        assertThat(httpSessionStub.getAttribute("key")).isNull();
    }

    @DisplayName("세션에 저장된 데이터 모두 삭제")
    @Test
    void test_invalidate() {
        // given
        Object original = new Object();
        httpSessionStub.setAttribute("key1", original);
        httpSessionStub.setAttribute("key2", original);
        // when
        httpSessionStub.invalidate();
        // then
        assertAll(
                () -> assertThat(httpSessionStub.getAttribute("key1")).isNull(),
                () -> assertThat(httpSessionStub.getAttribute("key2")).isNull()
        );
    }
}