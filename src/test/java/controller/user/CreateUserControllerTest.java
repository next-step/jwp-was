package controller.user;

import controller.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestBody;
import webserver.http.request.RequestHeader;
import webserver.http.request.RequestLine;
import webserver.http.response.HttpResponse;
import webserver.http.response.ResponseWriter;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CreateUserControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(CreateUserControllerTest.class);

    private Controller controller;

    @BeforeEach
    void controllerSetup() {
        controller = new CreateUserController();
    }

    @Test
    @DisplayName("회원가입 테스트")
    void testCreateUser() throws Exception {
        RequestLine requestLine = new RequestLine("POST /user/create HTTP/1.1");
        RequestHeader requestHeader = new RequestHeader(Map.of("Host", "localhost:8080", "Connection", "keep-alive", "Content-Length", "59", "Content-Type", "applcation/x-www-form-urlencoded", "Accept", "*/*"));
        RequestBody requestBody = new RequestBody("userId=aaaa&password=aaaa&name=aaaa&email=aaaa%40aaaa.com");

        HttpRequest httpRequest = new HttpRequest(requestLine, requestHeader, requestBody);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        HttpResponse httpResponse = controller.service(httpRequest);
        new ResponseWriter(out).process(httpResponse);

        assertThat(out.toString()).contains("HTTP/1.1 302 Found");
        assertThat(out.toString()).contains("Location: /index.html");
    }
}