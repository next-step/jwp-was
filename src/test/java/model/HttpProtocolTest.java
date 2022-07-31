package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.HttpProtocol;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class HttpProtocolTest {

    @Test
    @DisplayName("protocol 및 version 값 검증하기")
    void verifyProtocolAndVersion() {
        String protocol = "HTTP/1.1";

        HttpProtocol 비교값 = new HttpProtocol("HTTP", "1.1");
        HttpProtocol httpProtocol = HttpProtocol.Instance(protocol);

        assertThat(httpProtocol).isEqualTo(비교값);
    }

}
