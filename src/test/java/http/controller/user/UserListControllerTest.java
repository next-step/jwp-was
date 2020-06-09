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

public class UserListControllerTest {
    private String testFilePath = "./src/test/resources/UserListRequest.txt";
    private HttpRequest request;
    private HttpResponse response;
    private User user;

    @BeforeEach
    void setUp() throws IOException {
        InputStream in = new FileInputStream(new File(testFilePath));
        request = HttpRequest.parse(in, new HttpSessions());
        response = new HttpResponse();
        user = new User("seul", "test", "Eeseul Park", "seul");

        UserData.save(user);
    }

    @Test
    void list() throws Exception {
        UserListController controller = new UserListController();
        controller.doGet(request, response);

        ResponseHeader responseHeader = response.getHeader();

        assertThat(responseHeader.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(responseHeader.getContentType()).isEqualTo("text/html");
    }
}
