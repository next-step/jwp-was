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
    @DisplayName("빌더로 생성")
    void instance() {
        assertAll(
                () -> assertThatNoException().isThrownBy(() -> HttpResponse.Builder.ok().build()),
                () -> assertThatNoException().isThrownBy(() -> HttpResponse.Builder.notFound().build()),
                () -> assertThatNoException().isThrownBy(() -> HttpResponse.Builder.internalServerError().build()),
                () -> assertThatNoException().isThrownBy(() -> HttpResponse.Builder.ok("body").build()),
                () -> assertThatNoException().isThrownBy(() -> HttpResponse.Builder.ok().contentType("text/html"))
        );
    }

    @Test
    @DisplayName("콘텐츠 길이")
    void contentLength() {
        //given
        String body = "body";
        //when, then
        assertThat(HttpResponse.Builder.ok(body).build().contentLength())
                .isEqualTo(body.getBytes(StandardCharsets.UTF_8).length);
    }
}
