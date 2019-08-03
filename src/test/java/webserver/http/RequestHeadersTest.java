package webserver.http;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestHeadersTest {
    @ParameterizedTest
    @CsvSource(value = {"Host,localhost:8080", "Connection,keep-alive", "Accept,*/*"})
    void add(String input, String expected) {
        RequestHeaders requestHeaders = new RequestHeaders();
        requestHeaders.add("Host", "localhost:8080");
        requestHeaders.add("Connection", "keep-alive");
        requestHeaders.add("Accept", "*/*");

        assertThat(requestHeaders.get(input)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"Host,localhost:8080", "Connection,keep-alive", "Accept,*/*"})
    void parse_and_add(String input, String expected) {
        RequestHeaders requestHeaders = new RequestHeaders();
        requestHeaders.add("Host: localhost:8080");
        requestHeaders.add("Connection: keep-alive");
        requestHeaders.add("Accept: */*");

        assertThat(requestHeaders.get(input)).isEqualTo(expected);
    }

    // 널값, 공백이 올 경우
    @Test
    void add_null_value() {
        RequestHeaders requestHeaders = new RequestHeaders();
        requestHeaders.add("Host", null);
        requestHeaders.add("Connection", "  ");
        requestHeaders.add("Accept", "\n");

        assertThat(requestHeaders.keySet()).isEmpty();
    }
}
