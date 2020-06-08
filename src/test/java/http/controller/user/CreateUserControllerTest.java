package http.controller.user;

import http.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.ResponseHeader;
import model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.UserData;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateUserControllerTest {
    private String testFilePath = "./src/test/resources/CreateUserRequest.txt";
    private HttpRequest request;
    private HttpResponse response;
    private User user;

    @BeforeEach
    void setUp() throws IOException {
        InputStream in = new FileInputStream(new File(testFilePath));
        request = HttpRequest.parse(in);
        response = new HttpResponse();
        user = new User("seul", "test", "Eeseul Park", "seul");
    }

    @Test
    void doPostTest() {
        CreateUserController controller = new CreateUserController();
        controller.doPost(request, response);
        ResponseHeader responseHeader = response.getHeader();

        assertThat(UserData.getUser("seul")).isEqualTo(user);
        assertThat(responseHeader.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(responseHeader.getContentType()).isEqualTo("text/html");
        assertThat(responseHeader.getCustomHeader().get("Location")).isEqualTo("http://localhost:8080/index.html");
    }
}
