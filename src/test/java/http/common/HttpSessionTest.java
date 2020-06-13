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

}