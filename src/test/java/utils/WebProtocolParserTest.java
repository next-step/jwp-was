package utils;

import model.WebProtocol;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class WebProtocolParserTest {
    WebProtocolParser webProtocolParser = new WebProtocolParser();

    @Test
    @DisplayName("requestLine을 method, path(request-target), protocol, version으로 분리 할 수 있다.")
    void parsingTest() {
        //given
        String protocolWithVersion = "HTTP/1.1";
        String protocolType = "HTTP";
        String protocolVersion = "1.1";

        //when
        WebProtocol webProtocolParseResult = webProtocolParser.parse(protocolWithVersion);

        //then
        assertAll(
                () -> assertThat(webProtocolParseResult.getType()).isEqualTo(protocolType),
                () -> assertThat(webProtocolParseResult.getVersion()).isEqualTo(protocolVersion)
        );
    }
}