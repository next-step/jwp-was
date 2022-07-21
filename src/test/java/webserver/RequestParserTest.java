package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.domain.*;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestParserTest {

    @DisplayName("GET 요청을 파싱한다.")
    @Test
    void parseGetRequest(){
        String input = "GET /users HTTP/1.1";

        RequestLine requestLine = RequestParser.parseRequestLine(input);
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

        RequestLine requestLine = RequestParser.parseRequestLine(input);
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

    @DisplayName("URL을 파싱한다.")
    @Test
    void parseUrl(){
        String input = "/users?userId=javajigi&password=password&name=JaeSung";
        Map<String, String> expectedQueryParameters = Map.of(
                "userId", "javajigi",
                "password", "password",
                "name", "JaeSung"
        );

        Url url = RequestParser.parseUrl(input);

        assertAll(
                () -> assertEquals("/users", url.getPath()),
                () -> assertEquals(expectedQueryParameters, url.getQueryParameter().getParameters())
        );
    }
}
