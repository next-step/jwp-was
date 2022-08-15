package controller;

import db.DataBase;
import model.*;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class LoginControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(LoginControllerTest.class);
    String testDirectory = "./src/test/resources/";

    @Test
    void 로그인_성공() throws Exception {

        final LoginController controller = new LoginController();
        final User user = createUser();
        final HttpRequest httpRequest = createHttpRequest(user.getUserId());
        final HttpResponse httpResponse = createHttpResponse();

        DataBase.addUser(user);
        controller.service(httpRequest, httpResponse);

        logger.debug("respnse: {}", httpResponse);

    }

    @Test
    void 로그인_실패후_리다이렉트() throws Exception {
        final LoginController controller = new LoginController();
        final HttpRequest httpRequest = createHttpRequest("test");
        final HttpResponse httpResponse = createHttpResponse();
        controller.service(httpRequest, httpResponse);

        logger.debug("respnse: {}", httpResponse);
    }

    private User createUser() throws UnsupportedEncodingException {
        final String data = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        final RequestBody body = new RequestBody(data);

        return new User(body.getFirstValue("userId"), body.getFirstValue("password"), body.getFirstValue("name"), body.getFirstValue("email"));
    }

    private HttpRequest createHttpRequest(String userId) throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST.http"));
        return new HttpRequest(in);
    }

    private HttpResponse createHttpResponse() throws FileNotFoundException {
        return new HttpResponse();
    }
}
