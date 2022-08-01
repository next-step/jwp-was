package webserver.http.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("http 응답 코드")
class HttpStatusCodeTest {

    @DisplayName("http status line 패턴으로 문자열 반환 확인")
    @ParameterizedTest
    @EnumSource(HttpStatusCode.class)
    void toString_httpStatusLinePattern(HttpStatusCode code) {
        assertThat(code.toString()).containsPattern("\\d+ [a-zA-Z]+");
    }
}
