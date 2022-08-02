package webserver.controller;

import db.DataBase;
import enums.HttpStatusCode;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import support.Fixtures;
import webserver.Cookie;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginControllerTest {
    @Test
    @DisplayName("존재하는 유저가 로그인하면 logined=true 로 쿠키가 생성된다.")
    void loginSuccessTest() throws Exception {
        User user = new User("id", "password", "name", "email");
        DataBase.addUser(user);
        HttpRequest request = Fixtures.createHttpRequest();
        request.setBody(Fixtures.createRequestBody("userId=id&password=password&name=name&email=email"));

        HttpResponse result = new LoginController().execute(request);

        assertEquals(result.getStatusCode(), HttpStatusCode.FOUND);
        assertEquals(result.getHeaders().getHeader("Location"), "/index.html");
        assertEquals(result.getCookies().get("logined").toString(), new Cookie("logined", "true", "/").toString());
    }

    @Test
    @DisplayName("존재하는 유저가 로그인하면 logined=false 로 쿠키가 생성된다.")
    void loginFailTest() throws Exception {
        HttpRequest request = Fixtures.createHttpRequest();
        request.setBody(Fixtures.createRequestBody("userId=id&password=password&name=name&email=email"));

        HttpResponse result = new LoginController().execute(request);

        assertEquals(result.getStatusCode(), HttpStatusCode.FOUND);
        assertEquals(result.getHeaders().getHeader("Location"), "/user/login_failed.html");
        assertEquals(result.getCookies().get("logined").toString(), new Cookie("logined", "false", "/").toString());
    }
}
