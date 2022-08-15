package controller;

import model.*;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

public class UserListControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(UserListControllerTest.class);
    String testDirectory = "./src/test/resources/";

    @Test
    void 로그인_쿠키_존재시_정상응답() throws Exception {

        final HttpRequest httpRequest = createHttpRequest();
        final UserListController controller = new UserListController();
        final HttpResponse httpResponse = createHttpResponse();
        controller.service(httpRequest, httpResponse);

        logger.debug("respnse: {}", httpResponse);
    }

    @Test
    void 로그인_쿠키_없을경우_리다이렉트() throws Exception {

        final HttpRequest httpRequest = createHttpRequest();
        final UserListController controller = new UserListController();
        final HttpResponse httpResponse = createHttpResponse();
        controller.service(httpRequest, httpResponse);

        logger.debug("respnse: {}", httpResponse);
    }

    private HttpRequest createHttpRequest() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.http"));
        return new HttpRequest(in);
    }

    private HttpResponse createHttpResponse() throws FileNotFoundException {
        return new HttpResponse();
    }
}
