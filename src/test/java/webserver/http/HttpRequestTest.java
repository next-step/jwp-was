package webserver.http;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestTest.class);
    private static BufferedReader bufferedReader;

    @BeforeAll
    static void mockingBufferedReader() throws IOException {
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

        HttpHeaders httpHeaders = httpRequest.getHeaders();
        assertThat(httpHeaders.get("Host")).isEqualTo("localhost:8080");
        assertThat(httpHeaders.get("Connection")).isEqualTo("keep-alive");
        assertThat(httpHeaders.get("Accept")).isEqualTo("*/*");
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
