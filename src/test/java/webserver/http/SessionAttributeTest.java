package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("세션 속성")
class SessionAttributeTest {

    @Test
    @DisplayName("맵으로 생성")
    void instance() {
        assertAll(
                () -> assertThatNoException().isThrownBy(() -> SessionAttribute.from(Map.of("user", "any"))),
                () -> assertThatNoException().isThrownBy(SessionAttribute::empty)
        );
    }

    @Test
    @DisplayName("빈 여부")
    void isEmpty() {
        assertThat(SessionAttribute.from(Map.of("user", "any")).isEmpty()).isFalse();
    }

    @Test
    @DisplayName("비 포함 여부")
    void doesNotContain() {
        //given
        SessionAttribute attributeWithUser = SessionAttribute.from(Map.of("user", "any"));
        //when, then
        assertAll(
                () -> assertThat(attributeWithUser.doesNotContain("user")).isFalse(),
                () -> assertThat(attributeWithUser.doesNotContain("not")).isTrue()
        );
    }
}
