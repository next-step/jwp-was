package webserver.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class HttpParamsTest {

    @Test
    void create() {
        assertThat(new HttpParams("key=value&key2=value2")).isNotNull();
    }

    @Test
    void invalidQuerystring() {
        assertThatThrownBy(() -> new HttpParams("key=valuekey2=value2"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
