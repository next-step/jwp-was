package http.requestline.protocol;

import http.requestline.exception.IllegalRequestLineParsingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ProtocolSpecTest {

    @DisplayName("프로토콜 이름과 버전이 /로 나누어지지 않은 경우 IllegalRequestLineParsingException")
    @Test
    void split() {
        /* given */
        String protocolText = "HTTP";

        /* when */ /* then */
        assertThrows(IllegalRequestLineParsingException.class, () -> new ProtocolSpec(protocolText));
    }
}