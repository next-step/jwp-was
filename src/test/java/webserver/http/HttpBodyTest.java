package webserver.http;

import exception.ValidateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DisplayName("Http Body 테스트")
class HttpBodyTest {

    @Test
    @DisplayName("Body로 들어온 input 을 파싱한다.")
    void parse() {
        String input = "userId=javajigi&password=password&name=JaeSung&email=javajigi@slipp.net";

        HttpBody actual = HttpBody.from(input);

        assertThat(actual.getQueryStringMap()).hasSize(4);
        assertThat(actual.getQueryStringMap().get("userId")).isEqualTo("javajigi");
        assertThat(actual.getQueryStringMap().get("password")).isEqualTo("password");
        assertThat(actual.getQueryStringMap().get("name")).isEqualTo("JaeSung");
        assertThat(actual.getQueryStringMap().get("email")).isEqualTo("javajigi@slipp.net");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Body 에 null 혹은 빈값이 들어올 경우 파싱하지 않는다.")
    void parseFailedWhenInvalidInput(String input) {
        assertThatExceptionOfType(ValidateException.class)
                .isThrownBy(() -> HttpBody.from(input));
    }
}
