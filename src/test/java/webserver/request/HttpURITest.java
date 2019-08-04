package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class HttpURITest {

    @DisplayName("path를 구하는데 성공한다")
    @ParameterizedTest
    @ValueSource(strings = {
            "/users",
            "/users?userId=javajigi&password=password&name=JaeSung"})
    void createPath_success(String path) {
        // when
        HttpURI httpURI = HttpURI.of(path);

        // then
        assertThat(httpURI.getPath()).isEqualTo("/users");
    }

    @DisplayName("query 속성의 값을 구하는데 성공한다")
    @ParameterizedTest
    @CsvSource({
            "'userId', 'javajigi'",
            "'password', 'password'",
            "'name', 'JaeSung'",
    })
    void returnQueryString_success(String attribute, String value) {
        // given
        String url = "/users?userId=javajigi&password=password&name=JaeSung";

        // when
        HttpURI httpURI = HttpURI.of(url);
        String result = httpURI.getRequestParam(attribute);

        // then
        assertThat(result).isEqualTo(value);
    }
}