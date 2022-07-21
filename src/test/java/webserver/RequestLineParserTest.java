package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.domain.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestLineParserTest {

    @DisplayName("GET 요청을 파싱한다.")
    @Test
    void parseGetRequest(){
        String input = "GET /users HTTP/1.1";

        RequestLine requestLine = RequestLineParser.parseRequestLine(input);
        HttpMethod httpMethod = requestLine.getMethod();
        Path path = requestLine.getPath();
        ProtocolVersion protocolVersion = requestLine.getProtocolVersion();

        assertAll(
                () -> assertEquals(httpMethod, HttpMethod.GET),
                () -> assertEquals(path.getUrl(), "/users"),
                () -> assertEquals(protocolVersion.getProtocol(), Protocol.HTTP),
                () -> assertEquals(protocolVersion.getVersion(), HttpVersion.VER_1_1)
        );
    }
}
