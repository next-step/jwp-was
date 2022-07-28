package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

@DisplayName("HttpProtocol 단위 테스트")
class HttpProtocolTest {
    @DisplayName("프로토콜 형식이 맞지 않으면 예외를 발생한다.")
    @ParameterizedTest(name = "{displayName} - {arguments}")
    @ValueSource(strings = {
            "HTTP",
            "HTTP/1.1/1.1"
    })
    void createException(String protocol) {
        assertThatThrownBy(() -> new HttpProtocol(protocol))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(HttpProtocol.VALIDATION_MESSAGE);
    }
}
