package http.request.requestline.protocol;

import http.request.requestline.exception.RequestLineParsingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ProtocolSpecTest {

    @DisplayName("파라미터 null 체크하기")
    @Test
    void checkNull() {
        /* when */ /* then */
        assertThrows(RequestLineParsingException.class, () -> new ProtocolSpec(null));
    }

    @DisplayName("프로토콜 이름과 버전이 /로 나누어지지 않은 경우 예외 처리")
    @Test
    void split() {
        /* given */
        String protocolText = "HTTP";

        /* when */ /* then */
        assertThrows(RequestLineParsingException.class, () -> new ProtocolSpec(protocolText));
    }
}
