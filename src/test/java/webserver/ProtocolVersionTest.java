package webserver;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;

public class ProtocolVersionTest {

    @Test
    void 올바른_REQUEST_PROTOCOL_VERSION_테스트() {
        String requestProtocolVersion = "HTTP/1.1";
        ProtocolVersion protocolVersion = ProtocolVersion.from(requestProtocolVersion);

        assertAll(
                () -> Assertions.assertThat(protocolVersion.getProtocol()).isEqualTo("HTTP"),
                () -> Assertions.assertThat(protocolVersion.getVersion()).isEqualTo("1.1")
        );
    }
}
