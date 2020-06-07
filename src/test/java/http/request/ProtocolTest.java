package http.request;

import http.request.Protocol;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class ProtocolTest {

    @DisplayName("protocol과 version 을 파싱해 저장한다")
    @Test
    void parseProtocol() {
        Protocol protocol = new Protocol("HTTP/1.1");

        assertThat(protocol).isEqualTo(new Protocol("HTTP", "1.1"));
    }
}
