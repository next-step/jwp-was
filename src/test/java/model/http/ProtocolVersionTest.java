package model.http;

import exception.IllegalHttpRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProtocolVersionTest {

    @DisplayName("protocol은 protocol과 version으로 구성됩니다.")
    @Test
    void construct() {
        ProtocolVersion protocolVersion = new ProtocolVersion("HTTP/1.1");
        assertThat(protocolVersion.getProtocol()).isEqualTo(new Protocol("HTTP"));
        assertThat(protocolVersion.getVersion()).isEqualTo(new Version("1.1"));
    }

    @DisplayName("protocol은 protocol과 version으로 구성되며 '/'를 구분자로 하여 구분되어 있습니다. 유효하지 않은 경우 에러가 발생합니다.")
    @Test
    void construct_notValid() {
        assertThatThrownBy(() -> new ProtocolVersion("HTTP//1.1"))
                .isInstanceOf(IllegalHttpRequestException.class)
                .hasMessageContaining("protocolVersion에 들어가는 값은 protocol과 version만 포함되며 /는 하나만 사용됩니다.");
    }
}
