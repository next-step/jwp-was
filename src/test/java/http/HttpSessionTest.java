package http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpSessionTest {

    HttpSession session;
    String id;

    @BeforeEach
    void setSession() {
        id = UUID.randomUUID().toString();
        session = HttpSessions.getSession(id);
    }

    @ParameterizedTest
    @DisplayName("Session에 값을 세팅하고 제거 테스트")
    @CsvSource(value = {"name:jinwoo", "age:30"}, delimiter = ':')
    void getSessionAndRemoveTest(final String key, final String value) {
        session.setAttribute(key, value);
        assertThat(session.getAttribute(key)).isEqualTo(value);

        session.removeAttribute(key);
        assertThat(session.getAttribute(key)).isNull();
    }

    @Test
    @DisplayName("invalidate 함수 테스트")
    void sessionInvalidateTest() {
        session.setAttribute("name", "jinwoo");
        session.invalidate();

        HttpSession session = HttpSessions.getSession(id);
        assertThat(session.getAttribute("name")).isNull();
    }
}
