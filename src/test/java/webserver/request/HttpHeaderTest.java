package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import http.HttpHeader;

import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("요청 헤더 테스트")
class HttpHeaderTest {

    @DisplayName("요청 헤더 생성 테스트")
    @Test
    void createHeader_Success() {
        assertAll(
                () -> assertThatNoException().isThrownBy(
                        () -> HttpHeader.from(Collections.singletonList("Host: localhost:8080"))),
                () -> assertThatNoException().isThrownBy(
                        () -> HttpHeader.from(Map.of("Connection", "keep-alive")))
        );
    }

    @DisplayName("요청 헤더는 비어있을 수 있다.")
    @Test
    void headerCanBeEmpty() {
        assertThatNoException().isThrownBy(
                () -> HttpHeader.from(Collections.singletonList(""))
        );
        assertThat(HttpHeader.from(Collections.singletonList(""))).isNotNull();
    }

    @DisplayName("요청 헤더는 첫번째 :를 기준으로 나뉘어야 한다.")
    @Test
    void parseHeader() {
        HttpHeader header = HttpHeader.from(Map.of("Host", "localhost:8080"));
        assertThat(header.get("Host")).isEqualTo("localhost:8080");
    }

    @DisplayName("헤더 값 추가")
    @Test
    void addHeader() {
        HttpHeader header = HttpHeader.empty();

        assertThat(header.add("key", "value"))
                .isEqualTo(HttpHeader.from(Map.of("key", "value")));
    }
}
