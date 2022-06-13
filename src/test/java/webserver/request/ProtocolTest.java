package webserver.request;

import org.junit.jupiter.api.Test;
import webserver.request.Protocol;

import static org.junit.jupiter.api.Assertions.*;

class ProtocolTest {

    @Test
    void from() {
        // when
        Protocol protocol = Protocol.from("HTTP/1.1");

        // then
        assertAll(
                () -> assertEquals(protocol.getType(),"HTTP"),
                () -> assertEquals(protocol.getVersion(),"1.1")
        );
    }
}