package http.old;

import http.request.requestline.old.Protocol;
import http.request.requestline.old.ProtocolAndVersion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProtocolAndVersionTest {
    @DisplayName("ProtocolAndVersion 생성")
    @Test
    void createProtocol() {
        //given
        String strProtocol = "HTTP/1.1";

        //when
        ProtocolAndVersion protocolAndVersion = new ProtocolAndVersion(strProtocol);

        //then
        assertThat(protocolAndVersion).isEqualTo(new ProtocolAndVersion(Protocol.HTTP, "1.1"));
    }
}
