package domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.handler.Handler;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.Response;

import static support.FileSupporter.read;
import static support.FileSupporter.write;

class LoginHandlerTest {

    private Handler loginHandler;

    @BeforeEach
    void setUp() {
        loginHandler = new LoginHandler();
    }

    @DisplayName("기존에 회원이 없다면 로그인에 실패한다.")
    @Test
    void loginFail() throws Exception {
        try (final Response response = HttpResponse.of(write("Login_Fail_Response.txt"))) {
            loginHandler.handle(HttpRequest.of(read("Login.txt")), response);
        }
    }

    @DisplayName("기존에 회원이 있다면 로그인에 성공한다.")
    @Test
    void loginSuccess() throws Exception {
        CreateUserHandlerTest.createUser();

        try (final Response response = HttpResponse.of(write("Login_Success_Response.txt"))) {
            loginHandler.handle(HttpRequest.of(read("Login.txt")), response);
        }
    }
}
