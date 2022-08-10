package controller;

import db.DataBase;
import model.*;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtilsTest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(LoginControllerTest.class);

    @Test
    void 로그인_성공() throws IOException, URISyntaxException {

        final LoginController controller = new LoginController();
        final User user = createUser();
        final HttpRequest httpRequest = createHttpRequest(user.getUserId());

        DataBase.addUser(user);
        final HttpResponse response = controller.process(httpRequest);

        logger.debug("respnse: {}", response);

    }

    @Test
    void 로그인_실패후_리다이렉트() throws IOException, URISyntaxException {
        final LoginController controller = new LoginController();
        final HttpRequest httpRequest = createHttpRequest("test");

        final HttpResponse response = controller.process(httpRequest);

        logger.debug("respnse: {}", response);
    }

    private User createUser() throws UnsupportedEncodingException {
        final String data = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        final RequestBody body = new RequestBody(data);

        return new User(body.getOneValue("userId"), body.getOneValue("password"), body.getOneValue("name"), body.getOneValue("email"));
    }

    private HttpRequest createHttpRequest(String userId) throws UnsupportedEncodingException {

        final String requestBody = "userId=" + userId + "&password=password";

        return new HttpRequest(new HttpHeader(headers()), requestBody);
    }

    private List<String> headers() {
        return Arrays.asList("POST /user/create HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Content-Length: 59",
                "Content-Type: application/x-www-form-urlencoded",
                "Accept: */*");
    }
}
