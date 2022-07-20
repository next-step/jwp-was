package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class ProtocolTest {
    @DisplayName("유효한 입력값으로 Protocol 객체 생성하는 테스트")
    @Test
    void from() {
        Protocol protocol = Protocol.from("HTTP/1.1");
        assertThat(protocol).isNotNull();
        assertThat(protocol.getVersion()).isEqualTo("1.1");
    }

    @DisplayName("잘못된 입력값으로 Protocol 객체 생성하는 테스트")
    @Test
    void from_exception() {
        assertThatIllegalArgumentException().isThrownBy(() -> Protocol.from("HTTP//1.1"));
    }
}