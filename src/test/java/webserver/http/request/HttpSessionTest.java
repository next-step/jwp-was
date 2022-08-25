package webserver.http.request;


import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class HttpSessionTest {
    @Test
    public void 세션_값_저장() {
        HttpSession httpSession = new HttpSession(UUID.randomUUID());

        String key = "key";
        String value = "value";
        httpSession.setAttribute(key, value);

        assertThat(httpSession.getAttribute(key)).isEqualTo(value);
    }

    @Test
    public void 세션_값_삭제() {
        HttpSession httpSession = new HttpSession(UUID.randomUUID());

        String key = "key";
        String value = "value";
        httpSession.setAttribute(key, value);
        String returnValue = (String) httpSession.getAttribute(key);
        httpSession.removeAttribute(key);

        assertAll(
                () -> assertThat(returnValue).isEqualTo(value),
                () -> assertThat(httpSession.getAttribute(key)).isNull()
        );
    }

    @Test
    public void 세션_값_초기화() {
        HttpSession httpSession = new HttpSession(UUID.randomUUID());

        String key = "key";
        String value = "value";
        httpSession.setAttribute(key, value);
        String returnValue = (String) httpSession.getAttribute(key);
        httpSession.invalidate();

        assertAll(
                () -> assertThat(returnValue).isEqualTo(value),
                () -> assertThat(httpSession.getAttribute(key)).isNull()
        );
    }
}