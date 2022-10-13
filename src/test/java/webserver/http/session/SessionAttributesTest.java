package webserver.http.session;

import exception.NotFoundAttributeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Session Attribute 테스트")
class SessionAttributesTest {


    @Test
    @DisplayName("세션 값을 저장한다.")
    void setAttribute() {
        String key = "session";
        String attribute = "value";
        SessionAttributes sessionAttributes = new SessionAttributes();

        sessionAttributes.setAttribute(key, attribute);

        assertThat(sessionAttributes.getAttributesSize()).isEqualTo(1);
    }

    @Test
    @DisplayName("세션 값을 가져온다.")
    void getAttribute() {
        String key = "session";
        String attribute = "value";
        SessionAttributes sessionAttributes = new SessionAttributes();
        sessionAttributes.setAttribute(key, attribute);

        Object actual = sessionAttributes.getAttribute(key);

        assertThat(actual).isEqualTo(actual);
        assertThat(sessionAttributes.getAttributesSize()).isEqualTo(1);
    }

    @Test
    @DisplayName("존재하지 않는 세션 값을 가져올 경우 NotFoundAttributeException 이 발생한다.")
    void getAttributesFailed() {
        SessionAttributes sessionAttributes = new SessionAttributes();

        assertThatThrownBy(() -> sessionAttributes.getAttribute("session"))
                .isInstanceOf(NotFoundAttributeException.class);
    }

    @Test
    @DisplayName("세션 값을 제거한다.")
    void removeAttribute() {
        String key = "session";
        String attribute = "value";
        SessionAttributes sessionAttributes = new SessionAttributes();
        sessionAttributes.setAttribute(key, attribute);

        assertThat(sessionAttributes.getAttributesSize()).isEqualTo(1);

        sessionAttributes.removeAttribute(key);

        assertThat(sessionAttributes.getAttributesSize()).isEqualTo(0);
    }

    @Test
    @DisplayName("존재하지 않는 세션 값을 제거할 경우 NotFoundAttributeException 이 발생한다.")
    void removeAttributeFailed() {
        SessionAttributes sessionAttributes = new SessionAttributes();

        assertThatThrownBy(() -> sessionAttributes.removeAttribute("session"))
                .isInstanceOf(NotFoundAttributeException.class);
    }

    @Test
    @DisplayName("세션은 전부 비운다.")
    void invalidate() {
        String key = "session";
        String attribute = "value";
        SessionAttributes sessionAttributes = new SessionAttributes();
        sessionAttributes.setAttribute(key, attribute);

        assertThat(sessionAttributes.getAttributesSize()).isEqualTo(1);

        sessionAttributes.invalidate();

        assertThat(sessionAttributes.getAttributesSize()).isEqualTo(0);
    }
}
