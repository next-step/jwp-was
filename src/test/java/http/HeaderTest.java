package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeaderTest {

    @DisplayName("헤더는 이름과 값으로 파싱")
    @Test
    void test_createHeader_should_pass() {
        // given
        List<String> strings = Arrays.asList(
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*");
        // when
        Header header = new Header(strings);
        // then
        assertAll(
                () -> assertThat(header.size()).isEqualTo(3),
                () -> assertEquals("localhost:8080", header.get("Host")),
                () -> assertEquals("keep-alive", header.get("Connection")),
                () -> assertEquals("*/*", header.get("Accept"))
        );
    }

    @DisplayName("유효하지 않은 형식의 헤더는 IllegalArgumentException 발생")
    @Test
    void test_createHeader_should_fail() {
        // given
        List<String> strings = Arrays.asList("Connection-keep-alive");
        // when
        // then
        assertThatThrownBy(() -> {
            Header header = new Header(strings);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(Header.REQUEST_HEADER_IS_INVALID);
    }
}
