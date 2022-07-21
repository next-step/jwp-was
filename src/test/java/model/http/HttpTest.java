package model.http;

import exception.IllegalHttpRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpTest {

    @DisplayName("RequestLine에 유효하지 않는 데이터를 입력한 경우")
    @Test
    void construct_validationFail() {
        String requestLine = "GET /docs/index.html";

        assertThatThrownBy(() -> new Http(requestLine))
                .isInstanceOf(IllegalHttpRequestException.class)
                .hasMessageContaining("RequestLine은 method, pathInformation, protocol을 공백 기준으로 나누어 전송해야 합니다.");
    }

    @DisplayName("GET 요청 데이터를 전송한다.")
    @Test
    void httpRequest_GET() {
        String requestLine = "GET /docs/index.html HTTP/1.1";

        Http http = new Http(requestLine);
        assertThat(http.getMethod()).isEqualTo(Method.GET);
        assertThat(http.getPathInformation()).isEqualTo(new PathInformation("/docs/index.html"));
        assertThat(http.getProtocol()).isEqualTo(new ProtocolVersion("HTTP/1.1"));
    }

    @DisplayName("GET 요청 데이터를 전송한다.")
    @Test
    void httpRequest_POST() {
        String requestLine = "POST /users HTTP/1.1";

        Http http = new Http(requestLine);
        assertThat(http.getMethod()).isEqualTo(Method.POST);
        assertThat(http.getPathInformation()).isEqualTo(new PathInformation("/users"));
        assertThat(http.getProtocol()).isEqualTo(new ProtocolVersion("HTTP/1.1"));
    }

    @DisplayName("Query String으로 넘어가는 값을 파싱한다.")
    @Test
    void httpRequest_QueryString() {
        String requestLine = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";

        Http http = new Http(requestLine);
        assertThat(http.getMethod()).isEqualTo(Method.GET);
        assertThat(http.getPathInformation()).isEqualTo(new PathInformation("/users?userId=javajigi&password=password&name=JaeSung"));
        assertThat(http.getProtocol()).isEqualTo(new ProtocolVersion("HTTP/1.1"));
    }
}
