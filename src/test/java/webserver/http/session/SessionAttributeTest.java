package webserver.http.session;

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

    @DisplayName("attribute 값이 같은지 테스트")
    @Test
    void testSessionAttribute_GetAttribute() {
        assertThat(new SessionAttribute(attribute).getAttribute("userId"))
                .isEqualTo(attribute.get("userId"));
    }

    @DisplayName("attribute 세팅이 정상적으로 되는지 테스트")
    @Test
    void testSessionAttribute_SetAttribute() {
        String name = "password";
        String value = "aaaa";
        SessionAttribute sessionAttribute = new SessionAttribute(attribute);
        sessionAttribute.setAttribute(name, value);

        assertThat(sessionAttribute.getAttribute(name))
                .isEqualTo(value);
    }

    @DisplayName("특정 attribute 가 삭제되는지 테스트")
    @Test
    void testSessionAttribute_removeAttribute() {
        SessionAttribute sessionAttribute = new SessionAttribute(attribute);
        sessionAttribute.removeAttribute("userId");

        assertThat(sessionAttribute.getAttribute("userId")).isNull();
    }

    @DisplayName("전체 attribute 가 삭제되는지 테스트")
    @Test
    void testSessionAttribute_invalidate() {
        SessionAttribute sessionAttribute = new SessionAttribute(attribute);
        sessionAttribute.invalidate();

        assertThat(sessionAttribute.getAllAttribute().isEmpty()).isTrue();
    }
}