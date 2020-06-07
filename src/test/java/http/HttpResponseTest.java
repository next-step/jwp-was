package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
}