package domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.controller.Controller;
import webserver.http.AttributeName;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.Response;
import webserver.http.session.MockHttpSession;

import static support.FileSupporter.read;
import static support.FileSupporter.write;

class LoginControllerTest {

    private Controller loginController;
    private HttpRequest request;

    @BeforeEach
    void setUp() throws Exception {
        loginController = new LoginController();
        request = HttpRequest.of(read("Login.txt"));
        request.setSession(new MockHttpSession());
    }

    @DisplayName("세션에 유저 정보가 없다면 로그인에 실패한다.")
    @Test
    void loginFail() throws Exception {
        try (final Response response = HttpResponse.of(write("Login_Fail_Response.txt"))) {
            loginController.service(request, response);
        }
    }

    @DisplayName("세션에 유저 정보가 있다면 로그인에 성공한다.")
    @Test
    void loginSuccess() throws Exception {
        CreateUserControllerTest.createUser();

        try (final Response response = HttpResponse.of(write("Login_Success_Response.txt"))) {
            request.getSession().setAttribute(AttributeName.USER.toString(), new MockUser());

            loginController.service(request, response);
        }
    }
}

