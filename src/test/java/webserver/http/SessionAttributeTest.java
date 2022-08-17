package webserver.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class SessionAttributeTest {
    private Map<String, Object> attribute;

    @BeforeEach
    void setUp() {
        attribute = new HashMap<>();
        attribute.put("userId", "aaaa");
    }

    @DisplayName("Attribute 생성 테스트")
    @Test
    void testSessionAttribute() {
        SessionAttribute actual = new SessionAttribute(attribute);

        SessionAttribute expected = new SessionAttribute("userId", "aaaa");

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testSessionAttribute_GetAttribute() {
        assertThat(new SessionAttribute(attribute).getAttribute("userId"))
                .isEqualTo(attribute.get("userId"));
    }

    @Test
    void testSessionAttribute_SetAttribute() {
        String name = "password";
        String value = "aaaa";
        SessionAttribute sessionAttribute = new SessionAttribute(attribute);
        sessionAttribute.setAttribute(name, value);

        assertThat(sessionAttribute.getAttribute(name))
                .isEqualTo(value);
    }

    @Test
    void testSessionAttribute_removeAttribute() {
        SessionAttribute sessionAttribute = new SessionAttribute(attribute);
        sessionAttribute.removeAttribute("userId");

        assertThat(sessionAttribute.getAttribute("userId")).isNull();
    }

    @Test
    void testSessionAttribute_invalidate() {
        SessionAttribute sessionAttribute = new SessionAttribute(attribute);
        sessionAttribute.invalidate();
        assertThat(sessionAttribute.getAllAttribute().isEmpty()).isTrue();
    }
}