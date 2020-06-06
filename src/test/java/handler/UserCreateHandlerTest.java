package handler;

import static org.assertj.core.api.Assertions.assertThat;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.view.RedirectView;
import http.view.StaticResourceView;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import model.User;
import org.junit.jupiter.api.Test;

class UserCreateHandlerTest {

    @Test
    void create() throws IOException {
        HttpRequest httpRequest = getCreateHttpRequest();

        UserCreateHandler handler = new UserCreateHandler();
        handler.handle(httpRequest);

        User user = DataBase.findUserById("javajigi");
        assertThat(user.getUserId()).isEqualTo("javajigi");
    }

    @Test
    void create_redirect() throws IOException {
        HttpRequest httpRequest = getCreateHttpRequest();

        UserCreateHandler handler = new UserCreateHandler();
        HttpResponse httpResponse = handler.handle(httpRequest);

        assertThat(httpResponse).isEqualTo(new HttpResponse(new RedirectView("/index.html")));
    }

    @Test
    void login_success() throws IOException {
        DataBase.addUser(new User("javajigi", "password", "", ""));
        HttpResponse httpResponse = login("javajigi", "password");

        HttpResponse expect = new HttpResponse(new StaticResourceView("/index.html"));
        expect.addCookie("logined", "true");

        assertThat(httpResponse).isEqualTo(expect);
    }

    @Test
    void login_fail() {
        HttpResponse httpResponse = login("javajigi", "password");

        HttpResponse expect = new HttpResponse(new StaticResourceView("/user/login_failed.html"));
        assertThat(httpResponse).isEqualTo(expect);
    }

    private HttpResponse login(String userId, String password) {
        String line = "POST /user/login HTTP/1.1";
        String body = String.format("userId=%s&password=%s", userId, password);

        String contentLength = String.format("Content-Length: %s", body.length());
        String contentType = "Content-Type: application/x-www-form-urlencoded";

        HttpRequest httpRequest = HttpRequest.of(
            line,
            Arrays.asList(contentLength, contentType),
            body
        );

        UserLoginHandler handler = new UserLoginHandler();
        return handler.handle(httpRequest);
    }

    private HttpRequest getCreateHttpRequest() throws IOException {
        String line = "POST /user/create HTTP/1.1";
        String header = "Content-Length: 91\nContent-Type: application/x-www-form-urlencoded";
        String body = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        String raw = line + "\n" + header + "\n\n" + body;
        InputStream in = new ByteArrayInputStream(raw.getBytes());

        return HttpRequest.from(in);
    }
}
