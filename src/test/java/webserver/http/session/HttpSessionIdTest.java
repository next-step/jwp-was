package webserver.http.session;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class HttpSessionIdTest {

    @Test
    void create_and_id_length() {
        HttpSessionId httpSessionId = HttpSessionId.create();

        assertThat(httpSessionId.getId()).hasSize(32);
    }

    @Test
    void of_and_equals() {
        HttpSessionId httpSessionId = HttpSessionId.of("d77baf32c3fa4ad0a0e26f2a72d0f31e");
        HttpSessionId httpSessionId2 = HttpSessionId.of("d77baf32c3fa4ad0a0e26f2a72d0f31e");

        assertThat(httpSessionId).isEqualTo(httpSessionId2);
        assertThat(httpSessionId.getId()).isEqualTo(httpSessionId2.getId());
    }

    @Test
    void of_and_not_equals() {
        HttpSessionId httpSessionId = HttpSessionId.of("d77baf32c3fa4ad0a0e26f2a72d0f31e");
        HttpSessionId httpSessionId2 = HttpSessionId.of("d77baf32c3fa4ad0a0e26f2a72d0f31c");

        assertThat(httpSessionId).isNotEqualTo(httpSessionId2);
        assertThat(httpSessionId.getId()).isNotEqualTo(httpSessionId2.getId());
    }

    @Test
    void of_exception() {

        assertAll(
                () -> assertThatThrownBy(() -> HttpSessionId.of("d77baf32c3fa4ad0a0e26f2a72d0f31eee")).isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> HttpSessionId.of("D77baf32c3fA4ad0a0e26F2a72D0f31e")).isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> HttpSessionId.of("가나다baf32c3fA4ad0a0e26F2a72D0f31e")).isInstanceOf(IllegalArgumentException.class)
        );
    }

}
