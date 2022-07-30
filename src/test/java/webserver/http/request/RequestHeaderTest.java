package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpHeaders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("헤더")
class RequestHeaderTest {

    @Test
    @DisplayName("문자열 리스트나 맵으로 생성")
    void instance() {
        assertAll(
                () -> assertThatNoException()
                        .isThrownBy(() -> RequestHeader.from(Collections.singletonList("Host: localhost:8080"))),
                () -> assertThatNoException()
                        .isThrownBy(() -> RequestHeader.from(Map.of("Connection", "keep-alive")))
        );
    }

    @Test
    @DisplayName("null로 생성하면 빈 상태로 생성")
    void instance_null_emtpyHeader() {
        assertThat(RequestHeader.from((List<String>) null))
                .isEqualTo(RequestHeader.from(Collections.emptyMap()));
    }

    @Test
    @DisplayName("콘텐츠 길이 조회")
    void contentLength() {
        //given
        RequestHeader thenContentLength = RequestHeader.from(Map.of(HttpHeaders.CONTENT_LENGTH, "10"));
        //when, then
        assertThat(thenContentLength.contentLength()).isEqualTo(10);
    }

    @Test
    @DisplayName("콘텐츠 길이 기본값은 0")
    void contentLength_default() {
        assertThat(RequestHeader.from(Collections.emptyMap()).contentLength()).isZero();
    }

    @Test
    @DisplayName("문자열 리스트로 생성해서 값 조회")
    void value() {
        //given
        RequestHeader header = RequestHeader.from(
                Arrays.asList("Host: localhost:8080", "Accept: */*", "Cookie: logined=true")
        );
        //when, then
        assertAll(
                () -> assertThat(header.value("Host")).isEqualTo(Optional.of("localhost:8080")),
                () -> assertThat(header.value("Accept")).isEqualTo(Optional.of("*/*")),
                () -> assertThat(header.value("Cookie")).isEqualTo(Optional.of("logined=true"))
        );
    }

    @Test
    @DisplayName("쿠키 값 조회")
    void cookieValue() {
        //given
        RequestHeader header = RequestHeader.from(
                Arrays.asList("Host: localhost:8080", "Accept: */*", "Cookie: logined=true")
        );
        //when, then
        assertThat(header.cookieValue("logined")).isEqualTo(Optional.of("true"));
    }
}
