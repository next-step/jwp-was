package webserver.response;

import http.response.HttpStatusCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Http 응답 코드 테스트")
class HttpStatusCodeTest {

    private final static String REGEX = "\\d+ [a-zA-Z]+";

    @DisplayName("응답 코드는 숫자, 문자 형태")
    @ParameterizedTest
    @EnumSource(HttpStatusCode.class)
    void code(HttpStatusCode statusCode) {
        assertThat(statusCode.toString()).containsPattern(REGEX);
    }
}
