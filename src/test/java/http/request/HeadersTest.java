package http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HeadersTest {
    @DisplayName("헤더 key를 입력하면 헤더 Value 반환")
    @ParameterizedTest
    @CsvSource(value = {"Location:/index.html", "Content-Length:24"}, delimiter = ':')
    void getHeader(String key, String value) {
        //given
        Map<String, String> header = new HashMap<>();
        header.put(key, value);

        //when
        Headers headers = new Headers(header);

        //then
        assertThat(headers.getHeader(key)).isEqualTo(value);
    }

    @DisplayName("헤더 개수 반환")
    @Test
    void getSize() {
        //given
        Map<String, String> header = new HashMap<>();
        header.put("Location", "/index.html");
        header.put("Content-Length", "34");

        //when
        Headers headers = new Headers(header);

        //then
        assertThat(headers.getSize()).isEqualTo(2);
    }

    @DisplayName("Key 목록 반환")
    @Test
    void getKeySet() {
        //given
        Map<String, String> header = new HashMap<>();
        header.put("Location", "/index.html");
        header.put("Content-Length", "34");

        //when
        Headers headers = new Headers(header);

        //then
        assertThat(headers.getKeySet()).contains("Location", "Content-Length");
    }
}
