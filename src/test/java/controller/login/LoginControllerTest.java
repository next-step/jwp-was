package controller.login;

import controller.user.IntegrationControllerTest;
import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class LoginControllerTest extends IntegrationControllerTest {

    private LoginController loginController = new LoginController();

    @DisplayName("올바르지 않는 아이디와 비밀번호로 로그인 시도")
    @Test
    void loginWithWrongUserInfo() throws IOException {
        /* given */
        HttpRequest httpRequest = createHttpRequestFrom("/login/Login_Test.txt");
        ByteArrayOutputStream outputStream = createByteArrayOutputStream();

        /* when */
        HttpResponse httpResponse = loginController.service(httpRequest);
        httpResponse.flush(outputStream);

        /* then */
        String result = toStringFrom(outputStream);
        assertThat(result).contains("HTTP/1.1 302 FOUND");
        assertThat(result).contains("Location: /user/login_failed.html");
        assertThat(result).contains("Set-Cookie: logined=false");
    }

    @DisplayName("로그인 가능한 아이디와 비밀번호로 로그인하기")
    @Test
    void loginWithRightUserInfo() throws IOException {
        /* given */
        DataBase.addUser(User.builder()
                .userId("testid")
                .password("p@ssw0rd")
                .email("testid@gmail.com")
                .name("testname")
                .build());

        HttpRequest httpRequest = createHttpRequestFrom("/login/Login_Test.txt");
        ByteArrayOutputStream outputStream = createByteArrayOutputStream();

        /* when */
        HttpResponse httpResponse = loginController.service(httpRequest);
        httpResponse.flush(outputStream);

        /* then */
        String result = toStringFrom(outputStream);
        assertThat(result).contains("HTTP/1.1 302 FOUND");
        assertThat(result).contains("Location: /index.html");
        assertThat(result).contains("Set-Cookie: logined=true; Path=/");
    }
}
