package webserver.http;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpHeadersTest {
    @ParameterizedTest
    @CsvSource(value = {"Host,localhost:8080", "Connection,keep-alive", "Accept,*/*"})
    void add(String input, String expected) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Host", "localhost:8080");
        httpHeaders.add("Connection", "keep-alive");
        httpHeaders.add("Accept", "*/*");

        assertThat(httpHeaders.get(input)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"Host,localhost:8080", "Connection,keep-alive", "Accept,*/*"})
    void parse_and_add(String input, String expected) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Host: localhost:8080");
        httpHeaders.add("Connection: keep-alive");
        httpHeaders.add("Accept: */*");

        assertThat(httpHeaders.get(input)).isEqualTo(expected);
    }

    // 널값, 공백이 올 경우
    @Test
    void add_null_value() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Host", null);
        httpHeaders.add("Connection", "  ");
        httpHeaders.add("Accept", "\n");

        assertThat(httpHeaders.keySet()).isEmpty();
    }
}
