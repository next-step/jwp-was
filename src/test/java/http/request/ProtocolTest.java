package http.request;

import http.request.requestline.Protocol;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProtocolTest {

    @DisplayName("Protocol/Version Exception 테스트")
    @ParameterizedTest
    @ValueSource(strings = {
        "HTTP|1.0",
        "HTTP?1.0",
        "HTTP&1.0",
        "TCP/1.0",
        "HTTP/1.5",
    })
    @NullAndEmptySource
    void ofForException(String protocolAndVersion) {
        assertThrows(IllegalArgumentException.class, () -> Protocol.of(protocolAndVersion));
    }

    @DisplayName("Protocol/Version 테스트")
    @ParameterizedTest
    @CsvSource({
        "'HTTP', '1.0'",
        "'HTTP', '1.1'"
    })
    void of(String protocol, String version) {
        Protocol protocolObj = Protocol.of(protocol + "/" + version);

        assertThat(protocolObj.getProtocol()).isEqualTo(protocol);
        assertThat(protocolObj.getVersion()).isEqualTo(version);
    }
}