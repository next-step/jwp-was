package http;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class SessionAttributeTest {
    @Test
    @DisplayName("맵으로 생성")
    void create() {
        SessionAttribute sessionAttribute = new SessionAttribute(Map.of("user", "user"));

        assertThat(sessionAttribute.get("user")).isEqualTo("user");
    }

    @Test
    @DisplayName("빈 여부")
    void isEmpty() {
        assertThat(new SessionAttribute(Map.of("user", "user")).isEmpty()).isFalse();
    }

    @Test
    @DisplayName("비 포함 여부")
    void doesNotContain() {
        //given
        SessionAttribute attributeWithUser = new SessionAttribute(Map.of("user", "user"));
        //when, then
        assertAll(
                () -> assertThat(attributeWithUser.doesNotContain("user")).isFalse(),
                () -> assertThat(attributeWithUser.doesNotContain("not")).isTrue()
        );
    }
}