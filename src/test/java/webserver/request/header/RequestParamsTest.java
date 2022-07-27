package webserver.request.header;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import webserver.request.header.exception.InvalidRequestParamsException;
import webserver.request.header.RequestParams;

class RequestParamsTest {

    @DisplayName("= 로 key value가 나누어져 있지 않다면 생성이 불가능하다.")
    @CsvSource(value = {"keyValue", "key=Value=invalid", "key=", "=value"})
    @ParameterizedTest
    void createFailedByKeyValueDelimiter(String queryString) {
        // given
        // when
        // then
        assertThatThrownBy(() -> new RequestParams(queryString))
                .isInstanceOf(InvalidRequestParamsException.class)
                .hasMessage("유효하지 않은 Query String 입니다. key=value 형태여야 합니다.");
    }
}
