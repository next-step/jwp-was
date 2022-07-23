package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("http 요청")
class HttpRequestTest {

    @Test
    @DisplayName("문자열 리스트로 생성")
    void instance() {
        assertThatNoException().isThrownBy(() -> HttpRequest.from(
                Arrays.asList(
                        "GET /index.html HTTP/1.1",
                        "Host: localhost:8080",
                        "Connection: keep-alive"
                )
        ));
    }

    @Test
    @DisplayName("리스트는 비어있으면 안됨")
    void instance_empty_thrownIllegalArgumentException() {
        assertThatIllegalArgumentException().isThrownBy(() -> HttpRequest.from(Collections.emptyList()));
    }

    @Test
    @DisplayName("바디 추가")
    void withBody() {
        //given
        HttpRequest request = HttpRequest.from(List.of("GET /index.html HTTP/1.1"));
        RequestBody body = RequestBody.empty();
        //when, then
        assertThat(request.withBody(body)).isEqualTo(HttpRequest.of(request, body));
    }
}
