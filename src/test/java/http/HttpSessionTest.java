package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class HttpSessionTest {

    @DisplayName("session id 를 가져올 수 있다")
    @Test
    public void getId() {
        String id = UUID.randomUUID().toString();

        HttpSession httpSession = new HttpSession(id);

        assertThat(httpSession.getId()).isEqualTo(id);
    }

    @DisplayName("setAttribute 로 값을 저장하고 getAttribute 로 불러올 수 있다")
    @Test
    public void attribute() {
        String id = UUID.randomUUID().toString();
        String key = "name";
        String value = "jwee0330";

        HttpSession httpSession = new HttpSession(id);
        httpSession.setAttribute(key, value);

        Object foundAttribute = httpSession.getAttribute(key);
        String castAttribute = (String) foundAttribute;

        assertThat(castAttribute).isEqualTo(value);
    }

    @DisplayName("attribute 를 제거하면 기존 키에 매핑된 값을 리턴한다, 값이 없으면 널을 반환한다")
    @Test
    public void removeAttribute() {
        String id = UUID.randomUUID().toString();
        String key = "name";
        String value = "jwee0330";

        HttpSession httpSession = new HttpSession(id);
        httpSession.setAttribute(key, value);
        Object removedAttribute = httpSession.removeAttribute(key);
        String castAttribute = (String) removedAttribute;

        assertThat(castAttribute).isEqualTo(value);
        assertThat(httpSession.getAttribute(key)).isNull();
        assertThat(httpSession.removeAttribute(key)).isNull();
    }

    @DisplayName("현재 세션에 저장되어 있는 모든 값을 삭제한다")
    @Test
    public void invalidate() {
        String id = UUID.randomUUID().toString();
        String key = "name";
        String value = "jwee0330";

        HttpSession httpSession = new HttpSession(id);
        httpSession.setAttribute(key, value);

        httpSession.invalidate();

        assertThat(httpSession.getAttribute(key)).isNull();
        assertThatThrownBy(() -> httpSession.setAttribute(key, value))
                .isInstanceOf(UnsupportedOperationException.class);
    }

}
