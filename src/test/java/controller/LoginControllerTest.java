package controller;

import db.DataBase;
import model.*;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.RequestLine;

import java.io.*;

public class LoginControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(LoginControllerTest.class);
    String testDirectory = "./src/test/resources/";

    @Test
    void 로그인_성공() throws Exception {

        final LoginController controller = new LoginController();
        final User user = createUser();
        final HttpRequest httpRequest = createRequest("Http_POST.http");
        final HttpResponse httpResponse = createHttpResponse();

        DataBase.addUser(user);
        controller.service(httpRequest, httpResponse);

        logger.debug("respnse: {}", httpResponse);
    }

    @Test
    void 로그인_실패후_리다이렉트() throws Exception {
        final LoginController controller = new LoginController();
        final HttpRequest httpRequest = createRequest("Http_POST.http");
        final HttpResponse httpResponse = createHttpResponse();
        controller.service(httpRequest, httpResponse);

        logger.debug("respnse: {}", httpResponse);
    }

    private User createUser() throws UnsupportedEncodingException {
        final String data = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        final RequestBody body = new RequestBody(data);

        return new User(body.getFirstValue("userId"), body.getFirstValue("password"), body.getFirstValue("name"), body.getFirstValue("email"));
    }

    private HttpResponse createHttpResponse() throws FileNotFoundException {
        return new HttpResponse();
    }

    private HttpRequest createRequest(String fileName) throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + fileName));

        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        final RequestLine requestLine = new RequestLine(IOUtils.readRequestData(bufferedReader));
        final HttpHeader httpHeader = new HttpHeader(IOUtils.readHeaderData(bufferedReader));
        final RequestBody body = new RequestBody(IOUtils.readData(bufferedReader, httpHeader.getValueToInt("Content-Length")));

        return new HttpRequest(httpHeader, requestLine, body);
    }
}
