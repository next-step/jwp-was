package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.http.HttpMethod;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class HttpMethodTest {

    @DisplayName("요청 메서드를 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "get",
            "geT",
            "GET",
            "POST",
            "post"
    })
    void create(final String rawHttpMethod) {
        // when
        final HttpMethod httpMethod = HttpMethod.of(rawHttpMethod);

        // then
        assertThat(httpMethod).isNotNull();
    }

    @DisplayName("없는 메서드일 경우 예외처리한다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "g",
            "ASDAS",
            "fdsafdfa",
    })
    void notFound(final String rawHttpMethod) {
        // when / then
        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> HttpMethod.of(rawHttpMethod));
    }
}
