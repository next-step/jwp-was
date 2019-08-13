package webserver.http.controller;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpSession;
import webserver.http.HttpStatus;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.view.HandlebarViewResolver;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class UserListControllerTest {

    private UserListController<HandlebarViewResolver> userListController;

    @BeforeEach
    void setUp() {
        userListController = new UserListController<>(new HandlebarViewResolver());
    }

    @DisplayName("GET 요청 로그인 되어있을 경우")
    @Test
    void logined() throws IOException {
        String request = "GET /user/list HTTP/1.1\nAccept: */*\n";
        HttpRequest httpRequest = new HttpRequest(new ByteArrayInputStream(request.getBytes()));
        HttpResponse httpResponse = new HttpResponse(new ByteArrayOutputStream());

        User loginUser = new User("test", "test", "test", "test");

        HttpSession session = httpRequest.getSession();
        session.setAttribute("user", loginUser);
        DataBase.addUser(loginUser);

        userListController.doGet(httpRequest, httpResponse);

        assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("GET 요청 로그인 안되어있을 경우 redirect")
    @Test
    void notLogined() throws IOException {
        String request = "GET /user/list HTTP/1.1\nAccept: */*\n";
        HttpRequest httpRequest = new HttpRequest(new ByteArrayInputStream(request.getBytes()));
        HttpResponse httpResponse = new HttpResponse(new ByteArrayOutputStream());

        userListController.doGet(httpRequest, httpResponse);

        assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.REDIRECT);
    }

    @DisplayName("지원하지 않는 POST 요청 테스트")
    @Test
    void notAllowMethod() throws IOException {
        String request = "POST /user/list HTTP/1.1\nAccept: */*\n";
        HttpRequest httpRequest = new HttpRequest(new ByteArrayInputStream(request.getBytes()));
        HttpResponse httpResponse = new HttpResponse(new ByteArrayOutputStream());

        userListController.doPost(httpRequest, httpResponse);

        assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
    }
}