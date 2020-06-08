package session;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.*;

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


    @Test
    @DisplayName("세션의 ID 는 초기화 했을때의 ID 와 같다")
    void getId() {
        String sessionId = "origin";

        DefaultHttpSession httpSession = DefaultHttpSession.of(sessionId);

        assertThat(httpSession.getId()).isEqualTo(sessionId);
    }

    @Test
    @DisplayName("attribute 저장하고 불러오기")
    void setAndGetAttribute() {
        String key = "key";
        String value = "value";
        DefaultHttpSession httpSession = DefaultHttpSession.of("session key");

        assertThat(httpSession.getAttribute(key)).isNull();
        httpSession.setAttribute(key, value);
        assertThat(httpSession.getAttribute(key)).isEqualTo(value);
    }

    @Test
    @DisplayName("attribute 삭제")
    void removeAttribute() {
        String key = "key";
        String value = "value";
        DefaultHttpSession httpSession = DefaultHttpSession.of("session key");

        httpSession.setAttribute(key, value);
        assertThat(httpSession.getAttribute(key)).isEqualTo(value);

        httpSession.removeAttribute("key");
        assertThat(httpSession.getAttribute(key)).isNull();
    }
}