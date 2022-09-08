package model.http;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpSessionTest {

    @Test
    void 세션에_저장된_값을_찾을_수_있다() {
        final HttpSession httpSession = new HttpSession();

        final String name = "test";
        final String value = "value";
        httpSession.setAttribute(name, value);

        assertThat(httpSession.getAttribute(name)).isEqualTo(value);
    }

    @Test
    void 세션_name인자의_value_삭제한다() {
        final HttpSession httpSession = new HttpSession();

        final String name = "test";
        final String value = "value";
        httpSession.setAttribute(name, value);
        httpSession.removeAttribute(name);

        assertThat(httpSession.getAttribute(name)).isEqualTo(null);
    }

    @Test
    void 세션의_모든_값_삭제() {
        final HttpSession httpSession = new HttpSession();

        final String name = "test";
        final String value = "value";

        final String name1 = "test1";
        final String value1 = "value1";

        httpSession.setAttribute(name, value);
        httpSession.setAttribute(name1, value1);

        httpSession.invalidate();

        assertThat(httpSession.getAttribute(name)).isEqualTo(null);
        assertThat(httpSession.getAttribute(name1)).isEqualTo(null);
    }
}
