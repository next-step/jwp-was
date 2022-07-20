package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.request.Protocol;
import webserver.request.RequestURI;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProtocolParseTest {
    @Test
    @DisplayName("Protocol을 파싱하여 name과 version 정보를 얻을 수 있다.")
    void parseTest() {
        String protocol = "HTTP/1.1";

        Protocol result = new Protocol(protocol);

        assertEquals(result.getName(), "HTTP");
        assertEquals(result.getVersion(), "1.1");
    }
}
