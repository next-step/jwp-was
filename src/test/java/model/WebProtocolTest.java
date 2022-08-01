package model;

import org.junit.jupiter.api.Test;
import utils.parser.WebProtocolParser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class WebProtocolTest {

    @Test
    void getWebProtocolTest() {
        String protocolType = "HTTP";
        String protocolVersion = "1.1";
        WebProtocol webProtocol = new WebProtocol(protocolType, protocolVersion);

        String result = webProtocol.getWebProtocol();

        assertThat(result).isEqualTo("HTTP/1.1");
    }
}