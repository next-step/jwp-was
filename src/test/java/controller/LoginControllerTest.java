package controller;

import db.DataBase;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import model.HttpHeaders;
import model.RequestParameters;
import model.User;
import model.request.HttpRequest;
import model.request.RequestBody;
import model.response.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.RequestLine;

class LoginControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(LoginControllerTest.class);
    String testDirectory = "./src/test/resources/";

    @DisplayName("로그인 성공")
    @Test
    void loginSuccess() throws IOException {
        // given
        LoginController loginController = new LoginController();
        HttpRequest httpRequest = createRequest("Http_POST.http");
        HttpResponse httpResponse = createHttpResponse();

        User user = createUser();
        DataBase.addUser(user);
        loginController.service(httpRequest, httpResponse);

        logger.debug("response: {}", httpResponse);
    }

    @DisplayName("로그인 실패")
    @Test
    void loginFail() throws IOException {
        LoginController loginController = new LoginController();
        HttpRequest httpRequest = createRequest("Http_POST.http");
        HttpResponse httpResponse = createHttpResponse();
        loginController.service(httpRequest, httpResponse);

        logger.debug("response: {}", httpResponse);
    }

    private User createUser() throws IOException {
        String data = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        RequestParameters requestParameters = new RequestParameters(data);

        return new User(requestParameters.getValue("userId"), requestParameters.getValue("password"),
                requestParameters.getValue("name"), requestParameters.getValue("email"));
    }

    private HttpResponse createHttpResponse() {
        return HttpResponse.init();
    }

    private HttpRequest createRequest(String fileName) throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + fileName));

        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        RequestLine requestLine = (new RequestLine(br.readLine())).parse();
        HttpHeaders httpHeaders = new HttpHeaders(String.join("\n", IOUtils.readHeaderData(br)));
        RequestBody requestBody = new RequestBody(br, httpHeaders);

        return new HttpRequest(httpHeaders, requestLine, requestBody, null);
    }
}
