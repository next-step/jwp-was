package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class HttpProtocolTest {

    @Test
    @DisplayName("protocol 및 version 값 검증하기")
    void verifyProtocolAndVersion() {
        String protocol = "HTTP/1.1";

        HttpProtocol httpProtocol = HttpProtocol.Instance(protocol);

        assertAll(() -> {
            assertThat(httpProtocol.getProtocol()).isEqualTo("HTTP");
            assertThat(httpProtocol.getVersion()).isEqualTo("1.1");
        });
    }

}
