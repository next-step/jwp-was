package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName(("Headers 단위 테스트"))
class HeadersTest {
    @DisplayName("쿠키가 있는 헤더를 생성한다.")
    @Test
    void createWithCookie() {
        // given
        final Map<String, Object> headersMap = Map.of("Host", "localhost:8080", "Cookie", "key1=value1; key2=value2");

        // when
        final Headers headers = new Headers(headersMap);

        // then
        assertAll(
                () -> assertThat(headers.getHeader("Host")).isEqualTo("localhost:8080"),
                () -> assertThat(headers.getCookie("key1")).isEqualTo("value1"),
                () -> assertThat(headers.getCookie("key2")).isEqualTo("value2")
        );
    }

    @DisplayName("쿠키가 없는 헤더를 생성한다.")
    @Test
    void createWithoutCookie() {
        // given
        final Map<String, Object> headersMap = Map.of("Host", "localhost:8080");

        // when
        final Headers headers = new Headers(headersMap);

        // then
        assertThat(headers.getHeader("Host")).isEqualTo("localhost:8080");
    }
}
