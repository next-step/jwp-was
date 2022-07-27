package webserver.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class LoginControllerTest {

    @DisplayName("사용자 로그인이 실패하면, 302 Found 응답과 함께 login_failed.html로 리다이렉트한다.")
    @Test
    void loginFailure() throws IOException, URISyntaxException {
        String loginRequest = loginRequest();

        HttpRequest request = new HttpRequest(new ByteArrayInputStream(loginRequest.getBytes(StandardCharsets.UTF_8)));
        HttpResponse response = new HttpResponse(new ByteArrayOutputStream());

        LoginController controller = new LoginController();
        controller.handle(request, response);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeader("Location")).isEqualTo("/user/login_failed.html");
    }

    private String loginRequest() {
        String lineSeparator = System.lineSeparator();
        StringBuilder builder = new StringBuilder("POST /user/login HTTP/1.1 " + lineSeparator);
        builder.append("Content-Type: application/x-www-form-urlencoded").append(lineSeparator);
        builder.append("Content-Length: 26").append(lineSeparator);
        builder.append(lineSeparator);
        builder.append("userId=admin&password=1234").append(lineSeparator);
        return builder.toString();
    }

}
