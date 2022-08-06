package webserver;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class ProtocolTest {

    @Test
    @DisplayName("올바른 프로토콜 테스트")
    void correctProtocol() {
        String exampleProtocol = "HTTP/1.1";
        Protocol protocol = Protocol.parse(exampleProtocol);

        assertThat(protocol.getName()).isEqualTo("HTTP");
        assertThat(protocol.getVersion()).isEqualTo(1.1);
    }

    @Test
    @DisplayName("잘못된 프로토콜 테스트")
    void wrongProtocol() {
        String exampleProtocol_1 = "HTTP 1.1";
        String exampleProtocol_2 = "HTTP";
        String exampleProtocol_3 = "1.1";
        String exampleProtocol_4 = "HTTP//1.1";
        String exampleProtocol_5 = " ";

        assertThatIllegalArgumentException()
                .isThrownBy(() -> Protocol.parse(exampleProtocol_1));
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Protocol.parse(exampleProtocol_2));
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Protocol.parse(exampleProtocol_3));
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Protocol.parse(exampleProtocol_4));
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Protocol.parse(exampleProtocol_5));
    }
}
