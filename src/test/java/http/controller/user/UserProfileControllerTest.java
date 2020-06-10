package http.controller.user;

import http.HttpSessions;
import http.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.ResponseHeader;
import model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class UserProfileControllerTest {
    private String testFilePath = "./src/test/resources/UserProfileRequest.txt";
    private HttpRequest request;
    private HttpResponse response;
    private User user;

    @BeforeEach
    void setUp() throws IOException {
        InputStream in = new FileInputStream(new File(testFilePath));
        request = HttpRequest.getInstance(in, new HttpSessions());
        response = new HttpResponse();
    }

    @Test
    void doGet() {
        UserProfileController controller = new UserProfileController();
        controller.doGet(request, response);

        ResponseHeader responseHeader = response.getHeader();

        assertThat(responseHeader.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(responseHeader.getContentType()).isEqualTo("text/html");
    }
}
