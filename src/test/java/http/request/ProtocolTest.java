package http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class ProtocolTest {

    @DisplayName("protocol과 version 을 파싱해 저장한다")
    @Test
    void parseProtocol() {
        Protocol protocol = new Protocol("HTTP/1.1");

        assertThat(protocol).isEqualTo(new Protocol("HTTP", "1.1"));
    }

    @DisplayName("http 프로토콜이 아니면 에러를 떨군다.")
    @Test
    void parseHttpProtocol() {
        assertThatThrownBy(() -> {
            new Protocol("HHHH/1.1");
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("version이 유효하지 않으면 아니면 에러를 떨군다.")
    @Test
    void parseVersion() {
        assertThatThrownBy(() -> {
            new Protocol("HTTP/a.b");
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
