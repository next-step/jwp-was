package session;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@DisplayName("세션 attribute 를 관리하는 세션 객체")
class DefaultHttpSessionTest {

    @Test
    @DisplayName("초기화")
    void of() {
        String sessionId = "new id";

        assertThatCode(() -> DefaultHttpSession.of(sessionId)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("세션 키값이 null 이거나 빈 문자열일 경우에는 예외가 발생한다.")
    void ofException(final String sessionId) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> DefaultHttpSession.of(sessionId));
    }

}