package webserver.http;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RequestLineTest {
    @ParameterizedTest
    @ValueSource(strings = {"GET /user HTTP/1.1"})
    void parse(String input) {
        //when
        RequestLine requestLine = RequestLine.parse(input);

        //then
        assertThat(requestLine.getMethod()).isEqualTo("GET");
        assertThat(requestLine.getPath().getPath()).isEqualTo("/user");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 공백requestLine_예외처리(String input) {
        //then
        assertThrows(IllegalArgumentException.class, () -> {
            RequestLine.parse(input);
        });
    }
}