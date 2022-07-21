package webserver.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.*;

class HttpMethodTest {

    @DisplayName("String 값으로 HttpMethod Enum 을 가져온다.")
    @ParameterizedTest
    @CsvSource(value = {
            "GET, GET",
            "POST, POST",
            "PUT, PUT",
            "PATCH, PATCH",
            "DELETE, DELETE"})
    void of(String httpMethod, HttpMethod expected) {
        assertThat(HttpMethod.of(httpMethod)).isEqualTo(expected);
    }

    @DisplayName("잘못된 HttpMethod 문자열일 경우 예외가 발생한다")
    @Test
    void ofException() {
        assertThatIllegalArgumentException().isThrownBy(() -> HttpMethod.of("POSE"))
                        .withMessage("잘못된 HttpMethod 입니다. value = POSE");
    }
}
