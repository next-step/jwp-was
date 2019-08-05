package webserver.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class CookieTest {
    private static RequestStream requestStream;

    @BeforeEach
    private void makeGetRequestStream() {
        String request = "GET /create?userId=javajigi&password=password&name=박재성&email=javajigi%40slipp.net HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n" +
                "";

        requestStream = new RequestStream(new ByteArrayInputStream(request.getBytes()));
    }

    @Test
    void parse() throws IOException {
        HttpRequest httpRequest = HttpRequest.parse(requestStream);

        assertThat(Boolean.parseBoolean(httpRequest.cookieValue("logined")));
    }
}
