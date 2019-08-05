package webserver.http;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {
    private static BufferedReader bufferedReader;

    @BeforeEach
    void mockingBufferedReader() throws IOException {
        String requestString = "POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 69\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=박재성&email=javajigi%40slipp.net";

        bufferedReader = new BufferedReader(new StringReader(requestString));
    }

    @Test
    void httpHeadersTest() throws IOException {
        HttpRequest httpRequest = HttpRequest.parse(bufferedReader);

        assertThat(httpRequest.getHeaderValue("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequest.getHeaderValue("Connection")).isEqualTo("keep-alive");
        assertThat(httpRequest.getHeaderValue("Accept")).isEqualTo("*/*");
    }

    @Test
    void httpRequestBodyTest() throws IOException {
        HttpRequest httpRequest = HttpRequest.parse(bufferedReader);
        Map<String, String> userMap = new HashMap<>();
        userMap.put("userId", "javajigi");
        userMap.put("password", "password");
        userMap.put("name", "박재성");
        userMap.put("email", "javajigi@slipp.net");

        assertThat(httpRequest.getRequestBody().toString()).isEqualTo(userMap.toString());
    }
}
