package webserver.enums;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class RequestProtocolTest {

    @Test
    void createHttp_1_1_Test() {
        Protocol requestProtocol = Protocol.of("HTTP/1.1");

        assertThat(requestProtocol.protocol()).isEqualTo("HTTP");
        assertThat(requestProtocol.version()).isEqualTo("1.1");
    }

    @ParameterizedTest
    @CsvSource(value = {"HTTP#1.1", "HTTP", "HTP/1.1", "HTTP/"})
    void createInvalidProtocolTest(String invalidProtocol) {
        assertThatThrownBy(
            () -> Protocol.of(invalidProtocol)
        ).isInstanceOf(IllegalArgumentException.class);
    }

}
