package webserver.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("http 응답 코드")
class HttpStatusCodeTest {

    @ParameterizedTest
    @EnumSource(HttpStatusCode.class)
    void string(HttpStatusCode code) {
        assertThat(code.toString()).containsPattern("\\d+ [a-zA-Z]+");
    }
}
