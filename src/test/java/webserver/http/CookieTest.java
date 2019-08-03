package webserver.http;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class CookieTest {
    private static BufferedReader bufferedReader;

    @BeforeAll
    private static void makeGetRequestBufferedReader() throws IOException {
        bufferedReader = Mockito.mock(BufferedReader.class);
        Mockito.when(bufferedReader.readLine())
                .thenReturn(
                        "GET /create?userId=javajigi&password=password&name=박재성&email=javajigi%40slipp.net HTTP/1.1",
                        "Host: localhost:8080",
                        "Connection: keep-alive",
                        "Accept: */*",
                        "Cookie: logined=true",
                        ""
                );
    }

    @Test
    void parse() throws IOException {
        HttpRequest httpRequest = HttpRequest.parse(bufferedReader);

        Cookie cookie = httpRequest.getCookie();
        assertThat(Boolean.parseBoolean(cookie.get("logined")));
    }
}
