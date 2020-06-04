package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class ProtocolTest {

    @Test
    void parseProtocol() {
        Protocol protocol = new Protocol("HTTP/1.1");

        assertThat(protocol).isEqualTo(new Protocol("HTTP", "1.1"));
    }
}
