package webserver.http;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestURLTest {

    @Test
    void parse_shouldReturnPathAndQueryString() {
        RequestURL requestURL = RequestURL.parse("/users?userId=javajigi&password=password&name=JaeSung");
        assertThat(requestURL.getPath()).isEqualTo("/users");
        assertThat(requestURL.getQueryString()).isEqualTo("userId=javajigi&password=password&name=JaeSung");
    }

    @Test
    void parse_shouldReturnPath() {
        RequestURL requestURL = RequestURL.parse("/users");
        assertThat(requestURL.getPath()).isEqualTo("/users");
        assertThat(requestURL.getQueryString()).isEqualTo(null);
    }
}
