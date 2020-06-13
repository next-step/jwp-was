package webserver.session;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionTest {

    private final static String KEY = "KEY";
    private final static String VALUE = "VALUE";

    private HttpSession httpSession;

    @BeforeEach
    void setUp() {
        httpSession = new HttpSession();
    }

    @Test
    @DisplayName("HttpSession을 정상적으로 생성할 수 있다")
    void createHttpSession() {
        new HttpSession();
    }

    @Test
    @DisplayName("HttpSession를 생성하면 id가 할당된다")
    void createWithId() throws NoSuchFieldException, IllegalAccessException {
        final String result = fetchField(httpSession, "id");
        assertThat(result).isNotNull();
    }

    private String fetchField(HttpSession httpSession, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        final Class<HttpSession> clazz = HttpSession.class;
        final Field idField = clazz.getDeclaredField(fieldName);
        idField.setAccessible(true);
        return (String) idField.get(httpSession);
    }

    @Test
    @DisplayName("HttpSession에서 의 id는 UUID 형식이다")
    void HttpSessionId() {
        final String uuidRegex = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

        final String sessionId = httpSession.getId();
        final boolean result = sessionId.matches(uuidRegex);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("HttpSession에서 원하는 문자열 키로 객체를 저장하고 가져올 수 있다")
    void HttpSessionAttribute() {
        httpSession.setAttribute(KEY, VALUE);

        final Object result = httpSession.getAttribute(KEY);

        assertThat(result).isEqualTo(VALUE);
    }

    @Test
    @DisplayName("HttpSession에서 존재하지 않는 키로 attribute를 가져올 경우 null을 반환한다")
    void notExistAttributeKey() {
        final Object result = httpSession.getAttribute(KEY);

        assertThat(result).isEqualTo(null);
    }

    @Test
    @DisplayName("HttpSession에서 문자열 키로 attribute를 삭제할 수 있다")
    void HttpSessionremoveAttribute() {
        httpSession.setAttribute(KEY, VALUE);

        httpSession.removeAttribute(KEY);

        assertThat(httpSession.getAttribute(KEY)).isEqualTo(null);
    }

    @Test
    @DisplayName("HttpSession에서 문자열 키로 존재하지 않는 attribute를 삭제할 경우 에러를 반환하지 않는다")
    void HttpSessionRemoveNotExistAttributeKey() {
        httpSession.removeAttribute(KEY);
    }

    @Test
    @DisplayName("HttpSession에서 모든 attribute를 삭제할 수 있다")
    void invalidate() {
        httpSession.setAttribute(KEY, VALUE);

        httpSession.invalidate();

        assertThat(httpSession.getAttribute(KEY)).isEqualTo(null);
    }

}