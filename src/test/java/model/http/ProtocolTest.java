package model.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProtocolTest {

    @DisplayName("Protocol 객체를 생성한다.")
    @Test
    void construct() {
        Protocol protocol = new Protocol("HTTP");
        assertThat(protocol).isNotNull();
    }
}
