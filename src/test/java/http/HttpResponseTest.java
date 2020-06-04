package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Http response 객체에 대한 테스트")
class HttpResponseTest {

    @Test
    @DisplayName("ok 는 200 response를 보낸다.")
    void ok() {
        assertThat(HttpResponse.ok(null, null).getStatusCode()).isEqualTo(200);
    }

}