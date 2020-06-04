/*
package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.Socket;
import java.util.Arrays;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class RequestHandlerTest {

    private RequestHandler requestHandler = new RequestHandler(new Socket());

    @Test
    @DisplayName("파라미터가 없는 HTTP GET 요청 Parsing 테스트")
    void testRequestLinesWithoutParameters() {
        String[] requestLines = requestHandler.splitRequest("GET /users HTTP/1.1");
        assertThat(requestLines).containsExactly("GET", "/users", "HTTP/1.1");
    }

    @Test
    @DisplayName("파라미터가 있는 HTTP GET 요청 Parsing 테스트")
    void testRequestLinesWithParameters() {
        String[] requestLines = requestHandler.splitRequest("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1");
        assertThat(requestLines).containsExactly("GET", "/users?userId=javajigi&password=password&name=JaeSung", "HTTP/1.1");
    }

    @Test
    @DisplayName("QueryString Parsing 테스트")
    void testParseQueryString() {
        Map<String, String> queryString = requestHandler.parseQueryString(Arrays.asList("GET", "/users?userId=javajigi&password=password&name=JaeSung", "HTTP/1.1"));

        assertThat(queryString).contains(
                entry("userId", "javajigi"),
                entry("password", "password"),
                entry("name", "JaeSung")
        );
    }
}*/
