package webserver.controller;

import static java.nio.charset.StandardCharsets.*;
import static org.assertj.core.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.DataBase;
import exception.HttpMethodNotSupportedException;
import model.User;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;

class LoginControllerTest {

    private static final String VALID_USER_ID = "user";
    private static final String VALID_PASSWORD = "pass";
    private static final String INVALID_USER_ID = "user11";
    private static final String INVALID_PASSWORD = "pass11";

    @BeforeEach
    void setup() {
        DataBase.addUser(new User(VALID_USER_ID, VALID_PASSWORD, "유저", "user@example.com"));
    }

    @AfterEach
    void teardown() {
        DataBase.deleteAll();
    }

    @DisplayName("사용자 로그인 요청 시, HTTP 메서드가 GET인 경우 HttpMethodNotSupportedException 예외가 발생한다.")
    @Test
    void invalidHttpMethod() {
        String loginRequest = "GET /user/login HTTP/1.1";
        HttpRequest request = new HttpRequest(new ByteArrayInputStream(loginRequest.getBytes(UTF_8)));
        HttpResponse response = new HttpResponse(new ByteArrayOutputStream());

        LoginController controller = new LoginController();

        assertThatThrownBy(() -> controller.handle(request, response))
            .isInstanceOf(HttpMethodNotSupportedException.class);
    }

    @DisplayName("사용자 로그인이 성공하면, 302 Found 응답과 함께 index.html로 리다이렉트한다.")
    @Test
    void loginSuccess() throws IOException, URISyntaxException {
        String loginRequest = loginRequest(VALID_USER_ID, VALID_PASSWORD);

        HttpRequest request = new HttpRequest(new ByteArrayInputStream(loginRequest.getBytes(UTF_8)));
        HttpResponse response = new HttpResponse(new ByteArrayOutputStream());

        LoginController controller = new LoginController();
        controller.handle(request, response);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeader("Location")).isEqualTo("/index.html");
        assertThat(response.getHeader("Set-Cookie")).isEqualTo("logined=true; Path=/");
    }

    @DisplayName("사용자 로그인이 실패하면, 302 Found 응답과 함께 login_failed.html로 리다이렉트한다.")
    @Test
    void loginFailure() throws IOException, URISyntaxException {
        String loginRequest = loginRequest(INVALID_USER_ID, INVALID_PASSWORD);

        HttpRequest request = new HttpRequest(new ByteArrayInputStream(loginRequest.getBytes(UTF_8)));
        HttpResponse response = new HttpResponse(new ByteArrayOutputStream());

        LoginController controller = new LoginController();
        controller.handle(request, response);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeader("Location")).isEqualTo("/user/login_failed.html");
        assertThat(response.getHeader("Set-Cookie")).isEqualTo("logined=false");
    }

    private String loginRequest(String userId, String password) {
        String lineSeparator = System.lineSeparator();
        String requestBody = String.format("userId=%s&password=%s", userId, password);

        StringBuilder builder = new StringBuilder("POST /user/login HTTP/1.1 ").append(lineSeparator);
        builder.append("Content-Type: application/x-www-form-urlencoded").append(lineSeparator);
        builder.append(String.format("Content-Length: %d", requestBody.length())).append(lineSeparator);
        builder.append(lineSeparator);
        builder.append(requestBody);
        return builder.toString();
    }

}
