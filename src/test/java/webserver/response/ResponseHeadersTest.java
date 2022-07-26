package webserver.response;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class ResponseHeadersTest {

    @DisplayName("빈 문자열은 헤더에 추가할 수 없다")
    @ParameterizedTest(name = "#{index}: [{arguments}]")
    @NullAndEmptySource
    @ValueSource(strings = " ")
    void empty_key(String emptyKey) {
        //given
        final ResponseHeaders responseHeaders = new ResponseHeaders();

        responseHeaders.add(emptyKey, "value");

        assertThat(responseHeaders).isEqualTo(new ResponseHeaders());
    }

    @DisplayName("빈 문자열은 헤더에 추가할 수 없다")
    @ParameterizedTest(name = "#{index}: [{arguments}]")
    @NullAndEmptySource
    @ValueSource(strings = " ")
    void empty_value(String emptyValue) {
        //given
        final ResponseHeaders responseHeaders = new ResponseHeaders();

        responseHeaders.add("key", emptyValue);

        assertThat(responseHeaders).isEqualTo(new ResponseHeaders());
    }

    @DisplayName("헤더를 추가할 수 있다")
    @Test
    void add_header() {
        final ResponseHeaders responseHeaders = new ResponseHeaders();

        responseHeaders.add("Content-Type", "text/html");

        assertThat(responseHeaders).isEqualTo(new ResponseHeaders(Map.of("Content-Type", "text/html")));
    }

    @DisplayName("value가 없는 헤더는 추가할 수 없다")
    @Test
    void cannot_add_header_without_value() {
        final ResponseHeaders responseHeaders = new ResponseHeaders();

        responseHeaders.add("Content-Type", "");

        assertThat(responseHeaders).isEqualTo(new ResponseHeaders());
    }

    @DisplayName("등록된 헤더들을 조회할 수 있다")
    @Test
    void add_headers() {
        final ResponseHeaders responseHeaders = new ResponseHeaders();

        responseHeaders.add("Connection", "keep-alive");
        responseHeaders.add("Content-Encoding", "gzip");
        responseHeaders.add("Content-Type", "text/html; charset=UTF-8");

        org.junit.jupiter.api.Assertions.assertAll(
            () -> assertThat(responseHeaders.get("Connection")).isEqualTo("keep-alive"),
            () -> assertThat(responseHeaders.get("Content-Encoding")).isEqualTo("gzip"),
            () -> assertThat(responseHeaders.get("Content-Type")).isEqualTo("text/html; charset=UTF-8")
        );
    }

    @DisplayName("등록되지 않은 헤더를 조회하면 null을 반환한다")
    @Test
    void not_exist_header() {
        final ResponseHeaders responseHeaders = new ResponseHeaders();

        assertThat(responseHeaders.get("notExistHeader")).isNull();
    }

}
