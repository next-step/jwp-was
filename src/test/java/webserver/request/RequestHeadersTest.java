package webserver.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class RequestHeadersTest {

    @DisplayName("빈 문자열은 헤더에 추가할 수 없다")
    @ParameterizedTest(name = "#{index}: [{arguments}]")
    @NullAndEmptySource
    @ValueSource(strings = " ")
    void empty_headers(String emptyHeader) {
        //given
        final RequestHeaders requestHeaders = new RequestHeaders();

        requestHeaders.add(emptyHeader);

        assertThat(requestHeaders).isEqualTo(new RequestHeaders());
    }

    @DisplayName("헤더를 추가할 수 있다")
    @Test
    void add_header() {
        final RequestHeaders requestHeaders = new RequestHeaders();

        requestHeaders.add("Host: localhost:8080");

        assertThat(requestHeaders).isEqualTo(new RequestHeaders(Map.of("Host", "localhost:8080")));
    }

    @DisplayName("value가 없는 헤더는 추가할 수 없다")
    @Test
    void cannot_add_header_without_value() {
        final RequestHeaders requestHeaders = new RequestHeaders();

        requestHeaders.add("Host: ");

        assertThat(requestHeaders).isEqualTo(new RequestHeaders());
    }

    @DisplayName("등록된 헤더들을 조회할 수 있다")
    @Test
    void add_headers() {
        final RequestHeaders requestHeaders = new RequestHeaders();

        requestHeaders.add("Host: localhost:8080");
        requestHeaders.add("Connection: keep-alive");
        requestHeaders.add("Content-Length: 59");
        requestHeaders.add("Content-Type: application/x-www-form-urlencoded");
        requestHeaders.add("Accept: */*");

        org.junit.jupiter.api.Assertions.assertAll(
            () -> assertThat(requestHeaders.getHost()).isEqualTo("localhost:8080"),
            () -> assertThat(requestHeaders.getConnection()).isEqualTo("keep-alive"),
            () -> assertThat(requestHeaders.getContentLength()).isEqualTo(59),
            () -> assertThat(requestHeaders.getContentType()).isEqualTo("application/x-www-form-urlencoded"),
            () -> assertThat(requestHeaders.getAccept()).isEqualTo("*/*")
        );
    }

    @DisplayName("등록되지 않은 헤더를 조회하면 null을 반환한다")
    @Test
    void not_exist_header() {
        final RequestHeaders requestHeaders = new RequestHeaders();

        assertThat(requestHeaders.get("notExistHeader")).isNull();
    }

    @DisplayName("쿠키가 존재하는지 확인할 수 있다")
    @Test
    void has_cookie() {
        final RequestHeaders requestHeaders = new RequestHeaders();
        requestHeaders.add("Cookie: cookie=value");

        assertThat(requestHeaders.hasCookie("cookie=value")).isTrue();
    }

    @DisplayName("존재하지 않는 쿠키를 조회할 수 없다")
    @ParameterizedTest
    @CsvSource({
        "'', cookie",
        "cookie=value;, value"
    })
    void has_no_cookie(String cookie, String cookieName) {
        final RequestHeaders requestHeaders = new RequestHeaders();
        requestHeaders.add("Cookie: " + cookie);

        assertThat(requestHeaders.hasCookie(cookieName)).isFalse();
    }

    @DisplayName("존재하지 않는 쿠키를 조회하면 null을 반환한다")
    @Test
    void get_cookie_without_value() {
        final RequestHeaders requestHeaders = new RequestHeaders();

        assertThat(requestHeaders.getCookie("value")).isNull();
    }

    @DisplayName("쿠키를 조회할 수 있다")
    @Test
    void get_cookie() {
        final RequestHeaders requestHeaders = new RequestHeaders();
        requestHeaders.add("Cookie: cookie=value");

        assertThat(requestHeaders.getCookie("cookie")).isEqualTo("value");
    }

}
