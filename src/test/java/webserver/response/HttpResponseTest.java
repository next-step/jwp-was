package webserver.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("http 응답")
class HttpResponseTest {

    @Test
    @DisplayName("생성")
    void instance() {
        assertAll(
                () -> assertThatNoException().isThrownBy(() -> HttpResponse.of(HttpStatusCode.OK, ResponseHeader.empty())),
                () -> assertThatNoException().isThrownBy(() -> HttpResponse.of(HttpStatusCode.OK, ResponseHeader.empty(), new byte[0])),
                () -> assertThatNoException().isThrownBy(() -> HttpResponse.of(HttpStatusCode.OK, ResponseHeader.empty(), "body"))
        );
    }

    @Test
    @DisplayName("콘텐츠 길이")
    void contentLength() {
        //given
        String body = "body";
        //when, then
        assertThat(HttpResponse.of(HttpStatusCode.OK, ResponseHeader.empty(), body).contentLength())
                .isEqualTo(body.getBytes(StandardCharsets.UTF_8).length);
    }
}
