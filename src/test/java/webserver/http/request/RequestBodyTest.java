package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import webserver.http.request.RequestBody;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Http RequestBody 테스트")
class RequestBodyTest {

    @Test
    @DisplayName("RequestBody로 들어온 input 을 파싱한다.")
    void parse() {
        String input = "userId=javajigi&password=password&name=JaeSung&email=javajigi@slipp.net";

        RequestBody actual = RequestBody.from(input);

        assertThat(actual.getContent("userId")).isEqualTo("javajigi");
        assertThat(actual.getContent("password")).isEqualTo("password");
        assertThat(actual.getContent("name")).isEqualTo("JaeSung");
        assertThat(actual.getContent("email")).isEqualTo("javajigi@slipp.net");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("RequestBody 에 null 혹은 빈값이 들어올 경우 빈 ResponseBody를 응답한다..")
    void parseFailedWhenInvalidInput(String input) {
        RequestBody actual = RequestBody.from(input);

        assertThat(actual).isEqualTo(RequestBody.empty());
    }
}
