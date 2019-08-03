package webserver;

import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import webserver.http.HttpRequest;

import java.io.BufferedReader;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class RouterTest {
    private static BufferedReader bufferedReader;

    @BeforeAll
    static void mockingBufferedReader() throws IOException {
        bufferedReader = Mockito.mock(BufferedReader.class);
        Mockito.when(bufferedReader.readLine())
                .thenReturn(
                        "GET /create?userId=javajigi&password=password&name=박재성&email=javajigi%40slipp.net HTTP/1.1",
                        "Host: localhost:8080",
                        "Connection: keep-alive",
                        "Accept: */*",
                        ""
                );
    }
    @Test
    void route() throws IOException {
        HttpRequest httpRequest = HttpRequest.parse(bufferedReader);
        User user = new User("javajigi", "password", "박재성", "javajigi@slipp.net");

        assertThat(Router.route(httpRequest).apply(httpRequest).orElse("").toString()).isEqualTo(user.toString());
    }
}
