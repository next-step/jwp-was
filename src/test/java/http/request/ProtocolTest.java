package http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProtocolTest {
    @DisplayName("RequestLine에서 Protocol/Version 추출")
    @Test
    void getProtocolAndVersion() {
        //given
        String requestLine = "GET /user/create?name=seung&email=email@gmail.com HTTP/1.1";

        //when
        String protocol = Protocol.getProtocol(requestLine);
        String version = Protocol.getVersion(requestLine);

        //then
        assertThat(protocol).isEqualTo("HTTP");
        assertThat(version).isEqualTo("1.1");
    }
}
