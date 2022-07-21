package webserver.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestLineTest {

    @DisplayName("GET 요청을 파싱한다.")
    @Test
    void parseGetRequest(){
        String input = "GET /users HTTP/1.1";

        RequestLine requestLine = RequestLine.from(input);
        HttpMethod httpMethod = requestLine.getMethod();
        Url url = requestLine.getUrl();
        ProtocolVersion protocolVersion = requestLine.getProtocolVersion();

        assertAll(
                () -> assertEquals(HttpMethod.GET, httpMethod),
                () -> assertEquals("/users", url.getPath()),
                () -> assertEquals(Protocol.HTTP, protocolVersion.getProtocol()),
                () -> assertEquals(HttpVersion.VER_1_1, protocolVersion.getVersion())
        );
    }

    @DisplayName("POST 요청을 파싱한다.")
    @Test
    void parsePostRequest(){
        String input = "POST /users HTTP/1.1";

        RequestLine requestLine = RequestLine.from(input);
        HttpMethod httpMethod = requestLine.getMethod();
        Url url = requestLine.getUrl();
        ProtocolVersion protocolVersion = requestLine.getProtocolVersion();

        assertAll(
                () -> assertEquals(HttpMethod.POST, httpMethod),
                () -> assertEquals("/users", url.getPath()),
                () -> assertEquals(Protocol.HTTP, protocolVersion.getProtocol()),
                () -> assertEquals(HttpVersion.VER_1_1, protocolVersion.getVersion())
        );
    }
}
