package webserver.http.session;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionTest {
    private SessionAttribute sessionAttribute;
    private Map<String, Object> attribute;
    private UUID uuid;

    @BeforeEach
    void setUp() {
        attribute = new HashMap<>();
        attribute.put("userId", "aaaa");
        sessionAttribute = new SessionAttribute(attribute);
        uuid = UUID.randomUUID();
    }

    @DisplayName("HttpSession 생성 테스트")
    @Test
    void testHttpSessions() {
        assertThat(new HttpSession(uuid.toString(), sessionAttribute))
                .isEqualTo(new HttpSession(uuid.toString(), new SessionAttribute(attribute)));
    }

    @DisplayName("attribute 값이 같은지 테스트")
    @Test
    void testHttpSession_GetAttribute() {
        assertThat(new HttpSession(uuid.toString(), sessionAttribute).getAttribute("userId"))
                .isEqualTo(attribute.get("userId"));
    }

    @DisplayName("attribute 세팅이 정상적으로 되는지 테스트")
    @Test
    void testHttpSession_SetAttribute() {
        final String name = "password";
        final String value = "aaaa";

        HttpSession httpSession = new HttpSession(uuid.toString(), sessionAttribute);
        httpSession.setAttribute(name, value);

        assertThat(httpSession.getAttribute(name))
                .isEqualTo(value);
    }

    @DisplayName("특정 attribute 가 삭제되는지 테스트")
    @Test
    void testHttpSession_removeAttribute() {
        HttpSession httpSession = new HttpSession(uuid.toString(), sessionAttribute);
        httpSession.removeAttribute("userId");

        assertThat(httpSession.getAttribute("userId")).isNull();
    }

    @DisplayName("전체 attribute 가 삭제되는지 테스트")
    @Test
    void testHttpSession_invalidate() {
        HttpSession httpSession = new HttpSession(uuid.toString(), sessionAttribute);
        httpSession.invalidate();

        assertThat(httpSession.getAttribute("userId")).isNull();
    }

    @Test
    void name() {
        boolean b = Arrays.asList("a", "a", "a").stream()
                .allMatch(a -> a.equals("a"));

        System.out.println(b);
    }
}