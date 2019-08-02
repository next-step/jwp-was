package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class RequestQueryValueTest {

    @DisplayName("쿼리스트링 값을 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "a=bbb",
            "a=123",
            "a= ",
            "a=tt4tt3",
            "b=34g5tg432",
            "cdsfasd=!!!",
            "123=brb334g"
    })
    void create(final String rawQueryStringValue) {
        // when
        final RequestQueryValue requestQueryValue = RequestQueryValue.of(rawQueryStringValue);

        // then
        assertThat(requestQueryValue).isNotNull();
    }

    @DisplayName("쿼리스트링 생성 시 올바르지 않으면 예외처리한다.")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {
            "aasd",
            "=",
            " "
    })
    void throwInvalidQueryStringValueException(final String rawQueryStringValue) {
        // when / then
        assertThatExceptionOfType(InvalidQueryStringValueException.class)
                .isThrownBy(() -> RequestQueryValue.of(rawQueryStringValue));
    }
}
