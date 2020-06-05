package http.requestline.protocol;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProtocolSpecPoolTest {

    @DisplayName("HTTP/1.1 과 같이 동일한 프로토콜 스펙에 대해 캐싱이 되는지를 테스트")
    @Test
    void find() {
        /* given */
        String text = "HTTP/1.1";
        ProtocolSpec protocolSpec = ProtocolSpecPool.find(text);

        /* when */
        ProtocolSpec protocolSpec2 = ProtocolSpecPool.find(text);

        /* then */
        assertThat(protocolSpec).isEqualTo(protocolSpec2);
        assertThat(protocolSpec == protocolSpec2).isTrue();
    }
}