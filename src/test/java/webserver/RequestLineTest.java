package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class RequestLineTest {

    @DisplayName("Http Request Line 을 파싱하면 메서드, 경로, 프로토콜 정보를 알 수 있다.")
    @CsvSource(
            value = {
            "GET /users HTTP/1.1:GET:/users:HTTP:1.1",
            "POST /users HTTP/1.1:POST:/users:HTTP:1.1",},
            delimiter = ':')
    @ParameterizedTest
    void parseSuccessTest(String httpRequestLine, HttpMethod method, String path, String protocolName, String protocolVersion) {
        // given // when
        RequestLine requestLine = RequestLine.parseOf(httpRequestLine);

        // then
        assertThat(requestLine.getMethod()).isEqualTo(method);
        assertThat(requestLine.getProtocol()).isEqualTo(new HttpProtocol(protocolName, protocolVersion));
        assertThat(requestLine.getPath()).isEqualTo(path);
    }

    @DisplayName("Http Request Line 형식에 맞지 않으면 RequestLine 을 파싱할 수 없다.")
    @ValueSource(strings = {"GET /usersHTTP/1.1", "POST/users HTTP1.1", "POST /users*HTTP/1.1"})
    @ParameterizedTest
    void parseFailTest(String invalidRequestLine) {
        assertThatThrownBy(() -> RequestLine.parseOf(invalidRequestLine))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("RequestLine 파싱 실패 [" + invalidRequestLine + "]");
    }

}