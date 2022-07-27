package webserver.http.request;

import exception.InvalidRequestException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProtocolTest {
    @DisplayName("정상적인 Protocol이 들어왔을 때")
    @Test
    void getProtocol() {
        assertThat(new Protocol("HTTP/1.1")).isEqualTo(new Protocol("HTTP", "1.1"));
    }

    @DisplayName("비어있는 값이 들어왔을 때")
    @Test
    void getProtocol_WithNullProtocol() {
        Assertions.assertThatThrownBy(() -> new Version(""))
                .isInstanceOf(InvalidRequestException.class);
    }


}