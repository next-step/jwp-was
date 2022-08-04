package controller;

import db.DataBase;
import model.*;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LoginController 테스트")
class LoginControllerTest {

    private static LoginController loginController;

    @BeforeAll
    static void setUp() {
        loginController = new LoginController();
        DataBase.addUser(new User("javajigi","password","JaeSung","koola976@gmail.com"));
    }

    @DisplayName("로그인 성공")
    @Test
    void loginSuccess() {
        HttpRequest request = HttpRequest.of(
                RequestLine.of(HttpMethod.POST, Path.of("/user/login"), new String[]{"HTTP", "1.1"}),
                HttpRequestHeader.of(List.of("Host: www.nowhere123.com", "Accept: image/gif, image/jpeg, */*", "Accept-Language: en-us")),
                HttpRequestBody.of("userId=javajigi&password=password&name=JaeSung&email=koola976@gmail.com")
        );

        HttpResponse response = loginController.execute(request);

        assertAll(
                () -> assertThat(response.getHttpResponseCode()).isEqualTo("302 FOUND"),
                () -> assertThat(response.getHeaders()).contains(
                        Map.entry(HttpHeaders.LOCATION, "/index.html"),
                        Map.entry(HttpHeaders.SET_COOKIE, "logined=true; Path=/")
                )
        );
    }

    @DisplayName("로그인 실패")
    @Test
    void loginFail() {
        HttpRequest request = HttpRequest.of(
                RequestLine.of(HttpMethod.POST, Path.of("/user/login"), new String[]{"HTTP", "1.1"}),
                HttpRequestHeader.of(List.of("Host: www.nowhere123.com", "Accept: image/gif, image/jpeg, */*", "Accept-Language: en-us")),
                HttpRequestBody.of("userId=javajigi&password=pass1234&name=JaeSung&email=koola976@gmail.com")
        );

        HttpResponse response = loginController.execute(request);

        assertAll(
                () -> assertThat(response.getHttpResponseCode()).isEqualTo("302 FOUND"),
                () -> assertThat(response.getHeaders()).contains(
                        Map.entry(HttpHeaders.LOCATION, "/user/login_failed.html"),
                        Map.entry(HttpHeaders.SET_COOKIE, "logined=false; Path=/")
                )
        );
    }

}
