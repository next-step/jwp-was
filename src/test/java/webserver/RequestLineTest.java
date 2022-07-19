package webserver;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class RequestLineTest {

    @ParameterizedTest(name = "빈 문자열은 파싱할 수 없다. [{arguments}]")
    @NullAndEmptySource
    @ValueSource(strings = " ")
    void empty_strings_cannot_be_parsed(String requestLine) {
        assertThatThrownBy(() -> RequestLine.parse(requestLine))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("빈 문자열은 파싱할 수 없습니다.");
    }

}
