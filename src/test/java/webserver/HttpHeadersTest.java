package webserver;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.HttpHeaders.Parameter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class HttpHeadersTest {

    @DisplayName("헤더 정보를 생성한다")
    @ParameterizedTest
    @CsvSource({
            "'Host: localhost:8080', 'Host', 'localhost:8080'",
            "'Connection: keep-alive', 'Connection', 'keep-alive'",
            "'Accept: */*', 'Accept', '*/*'",
    })
    void createHeaders_success(String token, String expectedField, String expectedValue) {
        // when
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(token);
        // then
        assertThat(httpHeaders.get(expectedField)).isEqualTo(expectedValue);
    }

    @DisplayName("잘못된 데이터일 경우 exception")
    @ParameterizedTest
    @ValueSource(strings = {" ", "test=val", "OnlyKey"})
    void createHeaders_inputWrong_exception(String wrongInput) {
        // when && then
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> Parameter.of(wrongInput));
    }
}