package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProtocolVersionTest {

    @DisplayName("프로토콜 버젼은 숫자 또는 . 으로 구성되어야 한다.")
    @ValueSource(strings = {"", "1.1.", "."})
    @ParameterizedTest
    void versionCreateTest(String version) {
        assertThatThrownBy(() -> new ProtocolVersion(version))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("유효한 프로토콜 버전이 아님");
    }

    @DisplayName("HTTP 프로토콜의 버젼을 파싱할 수 있어야 한다.")
    @ValueSource(strings = {"TCP/1.1", "HTTT/1.1"})
    @ParameterizedTest
    void versionParseTest(String httpProtocol) {
        assertThatThrownBy(() -> ProtocolVersion.parseOf(httpProtocol))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("유효한 프로토콜 형식이 아님");
    }

}