package http.controller.user;

import http.HttpSessions;
import http.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.ResponseHeader;
import model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.UserData;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class UserLoginControllerTest {
    private String testFilePath = "./src/test/resources/UserLoginRequest.txt";
    private HttpRequest request;
    private HttpResponse response;
    private User user;

    @BeforeEach
    void setUp() throws IOException {
        InputStream in = new FileInputStream(new File(testFilePath));
        request = HttpRequest.getInstance(in, new HttpSessions());
        response = new HttpResponse();
        user = new User("seul", "test", "Eeseul Park", "seul");
        UserData.save(user);
    }

    @Test
    void doPost_logInSuccess() {
        user = new User("seul", "test", "Eeseul Park", "seul");
        UserData.save(user);

        LoginController controller = new LoginController();
        controller.doPost(request, response);

        ResponseHeader responseHeader = response.getHeader();

        assertThat(responseHeader.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(responseHeader.getContentType()).isEqualTo("text/html");
        assertThat(responseHeader.getCustomHeader().get("Set-Cookie")).contains("logined=true; Path=/");
        assertThat(responseHeader.getCustomHeader().get("Set-Cookie")).contains("JSESSONID");
    }

    @Test
    void doPost_logInFail() {
        user = new User("seul", "test123", "Eeseul Park", "seul");
        UserData.save(user);

        LoginController controller = new LoginController();
        controller.doPost(request, response);

        ResponseHeader responseHeader = response.getHeader();

        assertThat(responseHeader.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(responseHeader.getContentType()).isEqualTo("text/html");
        assertThat(responseHeader.getCustomHeader().get("Set-Cookie")).isEqualTo("logined=false; Path=/");
    }
}
