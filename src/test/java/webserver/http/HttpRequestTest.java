package webserver.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {
    private static RequestStream requestStream;

    @BeforeEach
    void mockingRequestStream() throws IOException {
        String requestString = "POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 69\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=박재성&email=javajigi%40slipp.net";

        requestStream = new RequestStream(new ByteArrayInputStream(requestString.getBytes()));
    }

    @Test
    void httpHeadersTest() throws IOException {
        HttpRequest httpRequest = HttpRequest.parse(requestStream);

        assertThat(httpRequest.getHeaderValue("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequest.getHeaderValue("Connection")).isEqualTo("keep-alive");
        assertThat(httpRequest.getHeaderValue("Accept")).isEqualTo("*/*");
    }

    @Test
    void httpRequestBodyTest() throws IOException {
        HttpRequest httpRequest = HttpRequest.parse(requestStream);
        Map<String, String> userMap = new HashMap<>();
        userMap.put("userId", "javajigi");
        userMap.put("password", "password");
        userMap.put("name", "박재성");
        userMap.put("email", "javajigi@slipp.net");

        assertThat(httpRequest.getRequestBody().toString()).isEqualTo(userMap.toString());
    }
}
