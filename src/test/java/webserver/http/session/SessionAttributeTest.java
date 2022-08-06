package webserver.http.session;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SessionAttributeTest {
    SessionAttribute sessionAttribute;
    String name = "key";
    String attribute = "value";

    @BeforeEach
    void setup() {
        sessionAttribute = new SessionAttribute();
    }

    @DisplayName("attribute get/set 테스트")
    @Test
    void getAttribute() {
        sessionAttribute.setAttribute(name, attribute);

        assertThat(sessionAttribute.getAttribute(name)).isEqualTo(attribute);
    }

    @DisplayName("attribute remove 테스트")
    @Test
    void removeAttribute() {
        sessionAttribute.remove(name);

        assertThat(sessionAttribute.getAttribute(name)).isNull();
    }

    @DisplayName("invalidate")
    @Test
    void invalidateAttribute() {
        sessionAttribute.setAttribute("key1", "test1");
        sessionAttribute.setAttribute("key2", "test2");

        sessionAttribute.clear();

        assertAll(
                () -> assertThat(sessionAttribute.getAttribute(name)).isNull(),
                () -> assertThat(sessionAttribute.getAttribute("key1")).isNull(),
                () -> assertThat(sessionAttribute.getAttribute("key2")).isNull()
        );
    }
}