package webserver.http.request.requestline;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProtocolTypeTest {
    @Test
    @DisplayName("ProtocolType 객체를 생성한다.")
    void create_ProtocolType() {
        ProtocolType protocolType = ProtocolType.HTTP;
        assertThat(protocolType).isNotNull().isInstanceOf(ProtocolType.class);
    }
}