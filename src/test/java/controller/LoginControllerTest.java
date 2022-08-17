package controller;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.*;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("LoginController 테스트")
class LoginControllerTest {

    private static LoginController loginController;

    @BeforeAll
    static void setUp() {
        loginController = new LoginController();
        DataBase.addUser(new User("javajigi", "password", "JaeSung", "javajigi@gmail.com"));
    }

    @DisplayName("로그인 성공")
    @Test
    void loginSuccess() {
        HttpRequest request = HttpRequest.of(
                RequestLine.of(HttpMethod.POST, Path.of("/user/login"), new String[]{"HTTP", "1.1"}),
                HttpRequestHeader.of(List.of("Host: www.nowhere123.com", "Accept: image/gif, image/jpeg, */*", "Accept-Language: en-us")),
                HttpRequestBody.of("userId=javajigi&password=password&name=JaeSung&email=javajigi@gmail.com")
        );

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        HttpResponse response = HttpResponse.of(dataOutputStream);

        HttpResponse result = loginController.execute(request, response);

        assertAll(
                () -> assertThat(result.getHttpResponseCode()).isEqualTo("302 FOUND"),
                () -> assertThat(result.getHeaders()).contains(
                        Map.entry(HttpHeaders.LOCATION, "/index.html"))
        );
    }

    @DisplayName("로그인 실패")
    @Test
    void loginFail() {
        HttpRequest request = HttpRequest.of(
                RequestLine.of(HttpMethod.POST, Path.of("/user/login"), new String[]{"HTTP", "1.1"}),
                HttpRequestHeader.of(List.of("Host: www.nowhere123.com", "Accept: image/gif, image/jpeg, */*", "Accept-Language: en-us")),
                HttpRequestBody.of("userId=javajigi&password=pass1234&name=JaeSung&email=javajigi@gmail.com")
        );

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        HttpResponse response = HttpResponse.of(dataOutputStream);

        HttpResponse result = loginController.execute(request, response);

        assertAll(
                () -> assertThat(result.getHttpResponseCode()).isEqualTo("302 FOUND"),
                () -> assertThat(result.getHeaders()).contains(
                        Map.entry(HttpHeaders.LOCATION, "/user/login_failed.html")
                )
        );
    }

}
