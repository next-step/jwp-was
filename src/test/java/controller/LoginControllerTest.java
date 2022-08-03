package controller;

import db.DataBase;
import model.*;
import org.junit.jupiter.api.Test;
import webserver.LoginController;
import webserver.RequestLine;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginControllerTest {

    @Test
    void 로그인_성공() throws IOException, URISyntaxException {

        final LoginController controller = new LoginController();
        final User user = createUser();
        final HttpRequest httpRequest = createHttpRequest();

        DataBase.addUser(user);
        final HttpResponse response = controller.process(httpRequest);

        assertThat(response.getMessages().get(0)).isEqualTo("HTTP/1.1 302 OK \r\n");
        assertThat(response.getMessages().get(2)).isEqualTo("Set-Cookie: logined=" + true + "; Path=/\r\n");
    }

    @Test
    void 로그인_실패후_리다이렉트() throws IOException, URISyntaxException {
        final LoginController controller = new LoginController();
        final HttpRequest httpRequest = createHttpRequest();

        final HttpResponse response = controller.process(httpRequest);

        assertThat(response.getMessages().get(0)).isEqualTo("HTTP/1.1 302 OK \r\n");
        assertThat(response.getMessages().get(2)).isEqualTo("Set-Cookie: logined=" + false + "; Path=/\r\n");
    }

    private User createUser() throws UnsupportedEncodingException {
        final String data = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        final RequestBody body = new RequestBody(data);

        return User.createUser(body);
    }
    private HttpRequest createHttpRequest() throws UnsupportedEncodingException {
        final String data = "POST /user/login HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 59\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password";

        final String requestBody = "userId=javajigi&password=password";

        return new HttpRequest(new RequestLine(data), requestBody);
    }
}
