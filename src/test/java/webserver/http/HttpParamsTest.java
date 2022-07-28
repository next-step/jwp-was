package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class HttpParamsTest {

    @DisplayName("문자열을 받아 HttpParam을 생성한다")
    @Test
    void create() {
        assertThat(new HttpParams("key=value&key2=value2")).isNotNull();
    }

    @DisplayName("잘못된 형식의 문자열의 경우 Exception이 발생한다")
    @Test
    void invalidQuerystring() {
        assertThatThrownBy(() -> new HttpParams("key=valuekey2=value2"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
