package webserver;

import model.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;

public class RouterTest {
    private String CREATE_USER_URL = "/user/create";
    private String LOGIN_URL = "/user/login";

    @Test
    @Disabled
    void route() throws IOException {
        BufferedReader bufferedReader = makeGetRequestBufferedReader();
        HttpRequest httpRequest = HttpRequest.parse(bufferedReader);
        HttpResponse httpResponse = new HttpResponse();
        User user = new User("javajigi", "password", "박재성", "javajigi@slipp.net");

        assertThat(Router.route(httpRequest, httpResponse).orElse("").toString()).isEqualTo(user.toString());
    }

    @Test
    void postRequestCreateUserTest() throws IOException {
        BufferedReader bufferedReader = makePostBufferedReader(CREATE_USER_URL, "userId=javajigi&password=password&name=박재성&email=javajigi%40slipp.net");
        HttpRequest httpRequest = HttpRequest.parse(bufferedReader);
        HttpResponse httpResponse = new HttpResponse();


        assertThat(Router.route(httpRequest, httpResponse).orElse("").toString())
                .isEqualTo("redirect:/index.html");
    }

    @Test
    void userLoginSuccessTest() throws IOException {
        createUser();
        BufferedReader bufferedReader = makeLoginSuccessBufferedReader();
        HttpRequest httpRequest = HttpRequest.parse(bufferedReader);
        HttpResponse httpResponse = new HttpResponse();


        assertThat(Router.route(httpRequest, httpResponse).orElse("").toString())
                .isEqualTo("redirect:/index.html");
    }

    @Test
    void userLoginFailTest() throws IOException {
        createUser();
        BufferedReader bufferedReader = makeLoginFailBufferedReader();
        HttpRequest httpRequest = HttpRequest.parse(bufferedReader);
        HttpResponse httpResponse = new HttpResponse();


        assertThat(Router.route(httpRequest, httpResponse).orElse("").toString())
                .isEqualTo("redirect:/user/login_failed.html");
    }

    private void createUser() throws IOException {
        BufferedReader bufferedReader = makePostBufferedReader(CREATE_USER_URL, "userId=javajigi&password=password&name=박재성&email=javajigi%40slipp.net");
        HttpRequest httpRequest = HttpRequest.parse(bufferedReader);
        HttpResponse httpResponse = new HttpResponse();

        Router.route(httpRequest, httpResponse);
    }

    private BufferedReader makeLoginSuccessBufferedReader() {
        return makePostBufferedReader(LOGIN_URL, "userId=javajigi&password=password");
    }

    private BufferedReader makeLoginFailBufferedReader() {
        return makePostBufferedReader(LOGIN_URL, "userId=javajigi&password=pas");
    }

    private BufferedReader makePostBufferedReader(String url, String data) {
        String requestString = "POST " + url + " HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: " + data.length() + "\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                data;

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
