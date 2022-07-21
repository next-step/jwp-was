package webserver;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class HttpMethodTest {

    @ParameterizedTest
    @ValueSource(strings = "GT, PST, PT, PAT, DEL")
    void 올바르지_못한_HTTP_METHOD_테스트(String httpMethod) {
        assertThatThrownBy(() -> HttpMethod.from(httpMethod))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("올바르지 못한 HTTP METHOD 입니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {"GET:GET", "POST:POST", "PUT:PUT", "PATCH:PATCH", "DELETE:DELETE"}, delimiter = ':')
    void 올바른_HTTP_METHOD_테스트(HttpMethod httpMethod, String method) {
        assertThat(HttpMethod.from(method)).isEqualTo(httpMethod);
    }
}
