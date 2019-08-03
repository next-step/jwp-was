package webserver;

import model.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import webserver.http.HttpRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;

public class RouterTest {

    @Test
    @Disabled
    void route() throws IOException {
        BufferedReader bufferedReader = makeGetRequestBufferedReader();
        HttpRequest httpRequest = HttpRequest.parse(bufferedReader);
        User user = new User("javajigi", "password", "박재성", "javajigi@slipp.net");

        assertThat(Router.route(httpRequest).apply(httpRequest).orElse("").toString()).isEqualTo(user.toString());
    }

    @Test
    void postRequestCreateUserTest() throws IOException {
        BufferedReader bufferedReader = makePostRequestBufferedReader();
        HttpRequest httpRequest = HttpRequest.parse(bufferedReader);
        User user = new User("javajigi", "password", "박재성", "javajigi@slipp.net");

        assertThat(Router.route(httpRequest).apply(httpRequest).orElse("").toString()).isEqualTo(user.toString());
    }

    private BufferedReader makePostRequestBufferedReader() {
        String requestString = "POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 69\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=박재성&email=javajigi%40slipp.net";

        return new BufferedReader(new StringReader(requestString));
    }

    private BufferedReader makeGetRequestBufferedReader() throws IOException {
        BufferedReader bufferedReader = Mockito.mock(BufferedReader.class);
        Mockito.when(bufferedReader.readLine())
                .thenReturn(
                        "GET /create?userId=javajigi&password=password&name=박재성&email=javajigi%40slipp.net HTTP/1.1",
                        "Host: localhost:8080",
                        "Connection: keep-alive",
                        "Accept: */*",
                        ""
                );

        return bufferedReader;
    }
}
