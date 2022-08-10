package controller;

import model.HttpHeader;
import model.HttpRequest;
import model.HttpResponse;
import model.ResponseBody;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserCreateControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(UserCreateControllerTest.class);

    @Test
    void 회원가입시_index_페이지_리다이렉트_응답() throws IOException, URISyntaxException {

        final UserCreateController controller = new UserCreateController();
        final HttpRequest httpRequest = createHttpRequest();
        final HttpResponse response = controller.process(httpRequest);

        logger.debug("respnse: {}", response);
    }

    private HttpRequest createHttpRequest() throws UnsupportedEncodingException {

        final String requestBody = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

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
