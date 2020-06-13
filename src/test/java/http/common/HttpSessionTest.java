package http.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionTest {

    @Test
    @DisplayName("HttpSession을 정상적으로 생성할 수 있다")
    void createHttpSession() {
        new HttpSession();
    }

    @Test
    @DisplayName("HttpSession를 생성하면 id가 할당된다")
    void createWithId() throws NoSuchFieldException, IllegalAccessException {
        final HttpSession httpSession = new HttpSession();

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
        final HttpSession httpSession = new HttpSession();

        final String sessionId = httpSession.getId();
        final boolean result = sessionId.matches(uuidRegex);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("HttpSession에서 원하는 문자열 키로 객체를 저장하고 가져올 수 있다")
    void HttpSessionAttribute() {
        final HttpSession httpSession = new HttpSession();
        final String key = "aa";
        final String value = "abc";
        httpSession.setAttribute(key, value);

        final Object result = httpSession.getAttribute(key);

        assertThat(result).isEqualTo(value);
    }

    @Test
    @DisplayName("HttpSession에서 존재하지 않는 키로 attribute를 가져올 경우 null을 반환한다")
    void notExistAttributeKey() {
        final HttpSession httpSession = new HttpSession();
        final String key = "a";

        final Object result = httpSession.getAttribute(key);

        assertThat(result).isEqualTo(null);
    }

}