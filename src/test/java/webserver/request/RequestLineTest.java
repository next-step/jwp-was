package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DisplayName("리퀘스트라인")
class RequestLineTest {

    @Test
    @DisplayName("GET 파싱 테스트(성공)")
    void parsingGETTest() throws Exception {
        RequestLine requestLine = RequestLine.findRequestLine("GET /docs/index.html HTTP/1.1");

        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo(Path.findPath("/docs/index.html"));
        assertThat(requestLine.getProtocolVersion()).isEqualTo(ProtocolVersion.findProtocolAndVersion("HTTP/1.1"));
    }

    @Test
    @DisplayName("POST 파싱 테스트(성공)")
    void parsingPOSTTest() throws Exception {
        RequestLine requestLine = RequestLine.findRequestLine("POST /docs/index.html HTTP/1.1");

        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(requestLine.getPath()).isEqualTo(Path.findPath("/docs/index.html"));
        assertThat(requestLine.getProtocolVersion()).isEqualTo(ProtocolVersion.findProtocolAndVersion("HTTP/1.1"));
    }

    @Test
    @DisplayName("잘못된 메소드 오류(실패)")
    void methodExceptionTest() throws Exception {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                    RequestLine.findRequestLine("PUT /test/test HTTP/1.1");
                }).withMessageContaining("올바르지 않은 HTTP 요청입니다.");
    }

}