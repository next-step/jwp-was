package http.response;

import http.StatusCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Map;

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
        httpResponse.setRedirect(location);

        assertThat(httpResponse.getLocation()).isEqualTo(location);
        assertThat(httpResponse.getStatusCode()).isEqualTo(StatusCode.REDIRECT);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("redirect 로 null 이나 빈 문자열을 넘기는 경우 예외 발생")
    void redirectFail(final String location) {
        HttpResponse httpResponse = HttpResponse.init();

        assertThatIllegalArgumentException()
                .isThrownBy(() -> httpResponse.setRedirect(location));
    }

    @Test
    @DisplayName("forward 는 200 ok response 이며 forward 경로가 존재한다")
    void forward() {
        HttpResponse httpResponse = HttpResponse.init();

        httpResponse.setForward("test");

        assertThat(httpResponse.getStatusCode()).isEqualTo(StatusCode.OK);
        assertThat(httpResponse.getForward()).isEqualTo("test");
    }

    @Test
    @DisplayName("response 에 model 값들을 담을 수 있다")
    void model() {
        HttpResponse httpResponse = HttpResponse.init();

        httpResponse.addModel("key", "value");

        Map<String, Object> models = httpResponse.getModels();
        assertThat(models).hasSize(1);
        assertThat(models.get("key")).isEqualTo("value");
    }
}