package webserver.http;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RequestLineTest {
    @ParameterizedTest
    @CsvSource({"GET /user HTTP/1.1,GET,/user", "POST /login?userId=test&password=good HTTP/1.1,POST,/login"})
    void parse(ArgumentsAccessor argumentsAccessor) {
        //when
        RequestLine requestLine = RequestLine.parse(argumentsAccessor.getString(0));

        //then
        assertThat(requestLine.getMethod()).isEqualTo(argumentsAccessor.get(1, String.class));
        assertThat(requestLine.getPath().getPath()).isEqualTo(argumentsAccessor.getString(2));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void handle_wrong_input(String input) {
        //then
        assertThrows(IllegalArgumentException.class, () -> {
            RequestLine.parse(input);
        });
    }
}