package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class HttpMethodTest {
    @ParameterizedTest
    @CsvSource(value = {
            "GET:GET",
            "POST:POST",
            "PUT:PUT",
            "PATCH:PATCH",
            "DELETE:DELETE"
    }, delimiter = ':')
    @DisplayName("문자열을 가지고 http method로 변환")
    void getHttpMethod(final String value, final HttpMethod method) {
        assertThat(HttpMethod.getHttpMethod(value)).isEqualTo(method);
    }

    @Test
    @DisplayName("http method에 속하지 않은 문자열로 에러 발생")
    void errorOnGettingHttpMethod() {
        assertThatThrownBy(() -> {
            HttpMethod.getHttpMethod("hope");
        }).isInstanceOf(IllegalArgumentException.class);
    }

}
