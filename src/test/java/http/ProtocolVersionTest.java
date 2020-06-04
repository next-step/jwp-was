package http;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Created by iltaek on 2020/06/03 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class ProtocolVersionTest {

    @ParameterizedTest
    @ValueSource(strings = {"HTTP/1.0", "HTTP/1.1"})
    void createProtocolVersionTest(String protocolVersionInput) {
        ProtocolVersion protocolVersion = new ProtocolVersion(protocolVersionInput);

        String[] values = protocolVersionInput.split("\\/");
        assertThat(protocolVersion).isEqualTo(new ProtocolVersion(protocolVersionInput));
        assertThat(protocolVersion.getHttpProtocol()).isEqualTo(values[0]);
        assertThat(protocolVersion.getVersion()).isEqualTo(values[1]);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "HTTP", "HTTP/", "HTTP/10.1", "HTTP/1.10", "HTTP/1.1.1"})
    void createInvalidProtocolVersionTest(String protocolVersionInput) {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            new ProtocolVersion(protocolVersionInput);
        }).withMessage(ProtocolVersion.ILLEGAL_PROTOCOL_VERSION);
    }
}
