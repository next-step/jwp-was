package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.http.HttpMethod;
import webserver.http.ProtocolVersion;
import webserver.http.RequestLine;
import webserver.http.RequestPath;

import static org.assertj.core.api.Assertions.*;

class RequestLineTest {

    @DisplayName("Http Request Line 을 파싱하면 메서드, 경로, 프로토콜 정보를 알 수 있다.")
    @CsvSource(
            value = {
            "GET /users HTTP/1.1:GET:/users:1.1",
            "POST /users HTTP/1.1:POST:/users:1.1",},
            delimiter = ':')
    @ParameterizedTest
    void parseSuccessTest(String httpRequestLine, HttpMethod method, String path, String protocolVersion) {
        // given // when
        RequestLine requestLine = RequestLine.parseOf(httpRequestLine);

        // then
        assertThat(requestLine.getMethod()).isEqualTo(method);
        assertThat(requestLine.getProtocolVersion()).isEqualTo(new ProtocolVersion(protocolVersion));
        assertThat(requestLine.getPath()).isEqualTo(new RequestPath(path));
    }

    @DisplayName("Http Request Line 형식에 맞지 않으면 RequestLine 을 파싱할 수 없다.")
    @ValueSource(strings = {"GET /usersHTTP/1.1", "POST/users HTTP1.1", "POST /users*HTTP/1.1"})
    @ParameterizedTest
    void parseFailTest(String invalidRequestLine) {
        assertThatThrownBy(() -> RequestLine.parseOf(invalidRequestLine))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("유효한 RequestLine 이 아님");
    }

}
