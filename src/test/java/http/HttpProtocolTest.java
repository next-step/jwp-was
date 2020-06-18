package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HttpProtocolTest {
    private HttpProtocol sut;
    @Test
    void parse_HTTP_RAW_TEXT() {
        // given
        final String protocol = "HTTP";
        final String version = "1.1";
        final String httpProtocolString = protocol + "/" + version;

        // when
        HttpProtocol httpProtocol = sut.from(httpProtocolString);

        // then
        assertThat(httpProtocol.getProtocol()).isEqualTo(protocol);
        assertThat(httpProtocol.getVersion()).isEqualTo(version);
    }
}