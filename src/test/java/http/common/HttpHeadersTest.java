package http.common;

import http.common.HttpCookie;
import http.common.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpHeadersTest {

    @DisplayName("헤더는 이름과 값으로 파싱")
    @Test
    void test_createHeader_should_pass() {
        // given
        List<String> strings = Arrays.asList(
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*");
        // when
        HttpHeaders httpHeaders = new HttpHeaders(strings);
        // then
        assertAll(
                () -> assertThat(httpHeaders.size()).isEqualTo(3),
                () -> assertEquals("localhost:8080", httpHeaders.get("Host")),
                () -> assertEquals("keep-alive", httpHeaders.get("Connection")),
                () -> assertEquals("*/*", httpHeaders.get("Accept"))
        );
    }

    @DisplayName("빈 리스로 빈 헤더 생성")
    @Test
    void test_createEmptyHeader() {
        // given
        // when
        HttpHeaders httpHeaders = new HttpHeaders(Collections.emptyList());
        // then
        assertThat(httpHeaders.size()).isEqualTo(0);
    }

    @DisplayName("유효하지 않은 형식의 헤더는 IllegalArgumentException 발생")
    @Test
    void test_createHeader_should_fail() {
        // given
        List<String> strings = Arrays.asList("Connection-keep-alive");
        // when
        // then
        assertThatThrownBy(() -> {
            HttpHeaders httpHeaders = new HttpHeaders(strings);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(HttpHeaders.REQUEST_HEADER_IS_INVALID);
    }

    @DisplayName("헤더의 키와 값을 \': \'로 이어붙이고, 각 헤더들은 개행문자로 이어붙인다.")
    @Test
    void test_joiningAll_should_pass() {
        // given
        List<String> strings = Arrays.asList(
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*");
        HttpHeaders httpHeaders = new HttpHeaders(strings);
        // when
        String result = httpHeaders.toJoinedString();
        // then
        assertAll(
                () -> assertThat(result).contains("Host: localhost:8080\r\n"),
                () -> assertThat(result).contains("Connection: keep-alive\r\n"),
                () -> assertThat(result).contains("Accept: */*\r\n")
        );
    }

    @DisplayName("헤더에 로그인 상태 쿠키값이 있는지 확인")
    @Test
    void test_getCookieValue() {
        // given
        List<String> strings = Arrays.asList("Cookie: logined=true");
        HttpHeaders httpHeaders = new HttpHeaders(strings);
        // when
        boolean result = httpHeaders.isLogin();
        // then
        assertThat(result).isTrue();
    }

    @DisplayName("헤더에 Cookie가 없으면 빈 쿠키 반환")
    @Test
    void test_getCookie() {
        // given
        List<String> strings = Arrays.asList(
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*");
        HttpHeaders httpHeaders = new HttpHeaders(strings);
        // when
        HttpCookie cookie = httpHeaders.getCookie();
        // then
        assertThat(cookie.size()).isEqualTo(0);
    }
}
