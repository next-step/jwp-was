package webserver.request.domain.request;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.exception.StringEmptyException;
import webserver.request.domain.request.ProtocolInfo;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ProtocolInfoTest {

    @Test
    @DisplayName("ProtocolInfo 가 빈 값이면 예외를 던진다.")
    public void validTest() {
        assertThrows(StringEmptyException.class, () -> {
            ProtocolInfo.parse("");
        });
    }

    @Test
    @DisplayName("requestLine에서 Protocol를 정상적으로 파싱했는지 확인하는 테스트")
    void methodTest() {
        Assertions.assertThat(ProtocolInfo.parse("HTTP/1.1")).isEqualTo(new ProtocolInfo("HTTP", "1.1"));
    }
}