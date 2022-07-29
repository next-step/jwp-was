package webserver.response;

import http.request.Protocol;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.HttpHeader;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("HTTP 응답 테스트")
class HttpResponseTest {

    @DisplayName("Http Response 생성")
    @Test
    void createHttpResponse() {
        StatusLine statusLine = StatusLine.of(Protocol.from("HTTP/1.1"), HttpStatusCode.OK);

        assertAll(
                () -> assertThatNoException().isThrownBy(() -> HttpResponse.of(statusLine, HttpHeader.empty())),
                () -> assertThatNoException().isThrownBy(() -> HttpResponse.of(statusLine, HttpHeader.empty(), new byte[0])),
                () -> assertThatNoException().isThrownBy(() -> HttpResponse.of(statusLine, HttpHeader.empty(), "body"))
        );
    }

    @DisplayName("Http Response 생성 실패")
    @Test
    void createHttpResponse_FAIL() {
        StatusLine statusLine = StatusLine.of(Protocol.from("HTTP/1.1"), HttpStatusCode.OK);
        assertAll(
                () -> assertThatThrownBy(() -> HttpResponse.of(null, HttpHeader.empty()))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> HttpResponse.of(statusLine, null, new byte[0]))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @DisplayName("Content-Length 테스트")
    @Test
    void checkContentLength() {
        StatusLine statusLine = StatusLine.of(Protocol.from("HTTP/1.1"), HttpStatusCode.OK);
        String body = "body";

        assertThat(HttpResponse.of(statusLine, HttpHeader.empty(), body).getContentLength())
                .isEqualTo(body.getBytes(StandardCharsets.UTF_8).length);
    }
}
