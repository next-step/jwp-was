package controller.user;

import controller.Controller;
import db.DataBase;
import model.User;
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
import webserver.http.session.HttpSession;
import webserver.http.session.SessionManagement;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class LoginControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(LoginControllerTest.class);

    private Controller controller;

    @BeforeEach
    void controllerSetup() {
        DataBase.addUser(new User("aaaa", "aaaa", "aaaa", "aaaa%40aaaa.com"));
        controller = new LoginController();
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void testLogin_WithSuccess() throws Exception {
        RequestLine requestLine = new RequestLine("POST /user/login HTTP/1.1");
        RequestHeader requestHeader = new RequestHeader(Map.of("Host", "localhost:8080", "Connection", "keep-alive", "Content-Length", "26", "Content-Type", "applcation/x-www-form-urlencoded", "Accept", "*/*"));
        RequestBody requestBody = new RequestBody("userId=aaaa&password=aaaa");

        HttpRequest httpRequest = new HttpRequest(requestLine, requestHeader, requestBody);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        HttpResponse httpResponse = controller.service(httpRequest);
        new ResponseWriter(out).process(httpResponse);
        HttpSession httpSession = SessionManagement.getSession(httpResponse.getCookie().getSessionId());

        assertThat(out.toString()).contains("HTTP/1.1 302 Found");
        assertThat(out.toString()).contains("Location: /index.html");
        assertThat(httpSession.getAttribute().toString()).contains("logined=true");
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    void testLogin_WithFail() throws Exception {
        RequestLine requestLine = new RequestLine("POST /user/login HTTP/1.1");
        RequestHeader requestHeader = new RequestHeader(Map.of("Host", "localhost:8080", "Connection", "keep-alive", "Content-Length", "26", "Content-Type", "applcation/x-www-form-urlencoded", "Accept", "*/*"));
        RequestBody requestBody = new RequestBody("userId=aaaa&password=bbbb");

        HttpRequest httpRequest = new HttpRequest(requestLine, requestHeader, requestBody);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        HttpResponse httpResponse = controller.service(httpRequest);
        new ResponseWriter(out).process(httpResponse);
        HttpSession httpSession = SessionManagement.getSession(httpResponse.getCookie().getSessionId());

        assertThat(out.toString()).contains("HTTP/1.1 302 Found");
        assertThat(out.toString()).contains("/user/login_failed.html");
        assertThat(httpSession.getAttribute().toString()).contains("logined=false");
    }

}