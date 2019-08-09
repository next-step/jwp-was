package domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.controller.Controller;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.Response;
import webserver.http.session.MockSessionStore;

import static support.FileSupporter.read;
import static support.FileSupporter.write;

class LoginControllerTest {

    private Controller loginController;

    @BeforeEach
    void setUp() {
        loginController = new LoginController();
    }

    @DisplayName("기존에 회원이 없다면 로그인에 실패한다.")
    @Test
    void loginFail() throws Exception {
        try (final Response response = HttpResponse.of(write("Login_Fail_Response.txt"))) {
            loginController.service(HttpRequest.of(read("Login.txt"), new MockSessionStore()), response);
        }
    }

    @DisplayName("기존에 회원이 있다면 로그인에 성공한다.")
    @Test
    void loginSuccess() throws Exception {
        CreateUserControllerTest.createUser();

        try (final Response response = HttpResponse.of(write("Login_Success_Response.txt"))) {
            loginController.service(HttpRequest.of(read("Login.txt"), new MockSessionStore()), response);
        }
    }
}
