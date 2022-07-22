package webserver.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProtocolInfoTest {

    @Test
    @DisplayName("requestLine에서 Protocol를 정상적으로 파싱했는지 확인하는 테스트")
    void methodTest() {
        Assertions.assertThat(ProtocolInfo.parse("HTTP/1.1")).isEqualTo(new ProtocolInfo("HTTP", "1.1"));
    }
}