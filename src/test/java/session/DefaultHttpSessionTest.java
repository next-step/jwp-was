package session;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("세션 attribute 를 관리하는 세션 객체")
class DefaultHttpSessionTest {

    @Test
    @DisplayName("초기화")
    void of() {
        String sessionId = "new id";

        assertThatCode(() -> DefaultHttpSession.of(sessionId)).doesNotThrowAnyException();
    }

}