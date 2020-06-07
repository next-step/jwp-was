package http.controller.user;

import http.HttpStatus;
import http.TestConstant;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.ResponseHeader;
import model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.UserData;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class UserListControllerTest {
    private HttpRequest request;
    private HttpResponse response;
    private User user;

    @BeforeEach
    void setUp() throws IOException {
        request = HttpRequest.getInstance(new ByteArrayInputStream(TestConstant.USER_LIST_REQUEST.getBytes()));
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
