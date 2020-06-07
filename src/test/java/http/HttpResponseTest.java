package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@DisplayName("Http response 객체에 대한 테스트")
class HttpResponseTest {

    @Test
    @DisplayName("ok 는 200 response를 보낸다.")
    void ok() {
        assertThat(HttpResponse.init().getStatusCode()).isEqualTo(StatusCode.OK);
    }

    @Test
    @DisplayName("redirect 는 302 response를 보낸다.")
    void redirect() {
        String location = "http://localhost:8080/index.html";
        HttpResponse httpResponse = HttpResponse.init();
        httpResponse.sendRedirect(location);

        assertThat(httpResponse.getLocation()).isEqualTo(location);
        assertThat(httpResponse.getStatusCode()).isEqualTo(StatusCode.REDIRECT);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("redirect 로 null 이나 빈 문자열을 넘기는 경우 예외 발생")
    void redirectFail(final String location) {
        HttpResponse httpResponse = HttpResponse.init();

        assertThatIllegalArgumentException()
                .isThrownBy(() -> httpResponse.sendRedirect(location));
    }

}