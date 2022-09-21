package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Http Body 테스트")
class HttpBodyTest {

    @Test
    @DisplayName("Body로 들어온 input 을 파싱한다.")
    void parse() {
        String input = "userId=javajigi&password=password&name=JaeSung&email=javajigi@slipp.net";

        HttpBody actual = HttpBody.from(input);

        assertThat(actual.getContents()).hasSize(4);
        assertThat(actual.getContents().get("userId")).isEqualTo("javajigi");
        assertThat(actual.getContents().get("password")).isEqualTo("password");
        assertThat(actual.getContents().get("name")).isEqualTo("JaeSung");
        assertThat(actual.getContents().get("email")).isEqualTo("javajigi@slipp.net");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Body 에 null 혹은 빈값이 들어올 경우 빈 ResponseBody를 응답한다..")
    void parseFailedWhenInvalidInput(String input) {
        HttpBody actual = HttpBody.from(input);

        assertThat(actual.getContents()).isEmpty();
    }
}
