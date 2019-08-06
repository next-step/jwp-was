package webserver;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.HttpHeaders.Parameter;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static webserver.response.HeaderProperty.SET_COOKIE;

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

    @DisplayName("헤더 정보를 반환한다")
    @Test
    void getHeader_success() {
        // given
        String valueOfCookie = "logined=true";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setCookie(valueOfCookie);

        // when
        String result = httpHeaders.get(SET_COOKIE.getHeaderName());

        // then
        assertThat(result).isEqualTo(valueOfCookie);
    }

    @DisplayName("헤더에 찾는 정보가 없을 경우 return null")
    @Test
    void getHeader_notExistValue_thenNull() {
        // given
        HttpHeaders httpHeaders = new HttpHeaders();

        // when
        String result = httpHeaders.get("noValue");

        // then
        assertThat(result).isNull();
    }

    @DisplayName("파라미터가 null or empty일 경우 생성에 실패한다")
    @ParameterizedTest
    @NullAndEmptySource
    void ofParameter_whenInputNull_exception(String wrong) {
        // exception
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Parameter.of(wrong));
    }
}