package webserver.enums;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class RequestProtocolTest {

    @Test
    void createHttp_1_1_Test() {
        RequestProtocol requestProtocol = RequestProtocol.of("HTTP/1.1");

        assertThat(requestProtocol.protocol()).isEqualTo("HTTP");
        assertThat(requestProtocol.version()).isEqualTo("1.1");
    }

}
