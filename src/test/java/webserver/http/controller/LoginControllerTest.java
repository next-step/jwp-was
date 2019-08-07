package webserver.http.controller;

import com.google.common.base.Charsets;
import db.DataBase;
import model.User;
import org.junit.jupiter.api.Test;
import webserver.http.Cookie;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.view.HtmlViewResolver;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class LoginControllerTest {

    private LoginController loginController = new LoginController(new HtmlViewResolver());

    @Test
    void 로그인_테스트() throws IOException {

        String loginRequest = "POST /user/login HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Content-Length: 25\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "Accept: */*\r\n" +
                "\r\n" +
                "userId=hoon&password=1234";

        ByteArrayInputStream in = new ByteArrayInputStream(loginRequest.getBytes(Charsets.UTF_8));
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        User loginUser = new User("hoon", "1234", "jeonghoon", "jeonghoon@test.com");
        DataBase.addUser(loginUser);

        HttpRequest httpRequest = new HttpRequest(in);
        HttpResponse httpResponse = new HttpResponse(out);
        loginController.service(httpRequest, httpResponse);

        Cookie cookie = httpResponse.getCookie("logined");

        assertThat(cookie).isNotNull();
        assertThat(cookie.getValue()).isEqualTo("true");

    }
}