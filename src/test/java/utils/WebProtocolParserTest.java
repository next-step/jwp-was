package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class WebProtocolParserTest {
    WebProtocolParser webProtocolParser = new WebProtocolParser();

    @Test
    @DisplayName("requestLine을 method, path(request-target), protocol, version으로 분리 할 수 있다.")
    void parsingTest() {
        //given
        String protocolWithVersion = "HTTP/1.1";
        String protocol = "HTTP";
        String protocolVersion = "1.1";

        //when
        Map<String, String> webProtocolParseResult = webProtocolParser.parse(protocolWithVersion);

        //then
        assertAll(
                () -> assertThat(webProtocolParseResult.get("protocol")).isEqualTo(protocol),
                () -> assertThat(webProtocolParseResult.get("protocolVersion")).isEqualTo(protocolVersion)
        );
    }
}