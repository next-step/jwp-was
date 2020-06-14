package http;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Created by iltaek on 2020/06/11 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
class ProtocolVersionTest {

    @ParameterizedTest
    @ValueSource(strings = {"HTTP/1.0", "HTTP/1.1"})
    @DisplayName("유효한 입력값으로 ProtocolVersion 이 정상적으로 생성되는지 테스트")
    void createProtocolVersionTest(String protocolVersionInput) {
        ProtocolVersion protocolVersion = new ProtocolVersion(protocolVersionInput);

        String[] values = protocolVersionInput.split("/");
        assertThat(protocolVersion).isEqualTo(new ProtocolVersion(protocolVersionInput));
        assertThat(protocolVersion.getHttpProtocol()).isEqualTo(values[0]);
        assertThat(protocolVersion.getVersion()).isEqualTo(values[1]);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "HTTP", "HTTP/", "HTTP/10.1", "HTTP/1.10", "HTTP/1.1.1"})
    @DisplayName("유효하지 않은 입력값으로 ProtocolVersion 을 생성 시에 지정한 예외가 발생하는지 테스트")
    void createInvalidProtocolVersionTest(String protocolVersionInput) {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            new ProtocolVersion(protocolVersionInput);
        }).withMessage(ProtocolVersion.ILLEGAL_PROTOCOL_VERSION);
    }
}
