package webserver.http;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import request.HttpMethod;
import request.RequestLine;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by youngjae.havi on 2019-08-01
 */
public class RequestLineTest {

    @Test
    void basic_parsing() {
        RequestLine requestLine = RequestLine.parse("GET /users HTTP/1.1");

        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getHttpVersion()).isEqualTo("HTTP/1.1");
    }

    @Test
    void query_string_parsing() {
        RequestLine requestLine = RequestLine.parse("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1");

        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getQueryString()).hasSize(3);
        assertThat(requestLine.getQueryString()).containsKeys("userId", "password", "name");
        assertThat(requestLine.getHttpVersion()).isEqualTo("HTTP/1.1");
    }

    @Test
    void not_include_query_string_parsing() {
        RequestLine requestLine = RequestLine.parse("GET /users HTTP/1.1");

        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getQueryString()).hasSize(0);
        assertThat(requestLine.getHttpVersion()).isEqualTo("HTTP/1.1");
    }

    @ParameterizedTest
    @CsvSource({"userId, javajigi"})
    public void parameterized_test(String key, String value) {
        RequestLine requestLine = RequestLine.parse("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1");
        assertThat(requestLine.getQueryString()).containsKeys(key);
        assertThat(requestLine.getQueryString()).containsValue(Collections.singletonList(value));
    }

    @Test
    void illegal_method() {
        assertThrows(IllegalArgumentException.class, () -> RequestLine.parse("GOT /users HTTP/1.1"));
    }
}
