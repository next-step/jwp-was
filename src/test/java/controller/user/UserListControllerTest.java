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

import static model.Constant.JSESSIONID;
import static org.assertj.core.api.Assertions.assertThat;

class UserListControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(UserListControllerTest.class);

    private Controller controller;
    private HttpSession httpSession = SessionManagement.save();

    @BeforeEach
    void setUp() {
        DataBase.addUser(new User("aaaa", "aaaa", "aaaa", "aaaa%40aaaa.com"));
        controller = new UserListController();
    }

    @Test
    @DisplayName("유저목록 확인 - 로그인한 상태")
    void testUserList_WithSuccess() throws Exception {
        httpSession.setAttribute("logined", "true");
        String sessionId = String.format(JSESSIONID + "=%s; Path=/", httpSession.getId());
        SessionManagement.setSessionAttribute(httpSession);

        RequestLine requestLine = new RequestLine("GET /user/list?userId=aaaa&password=aaaa&name=aaaa HTTP/1.1");
        RequestHeader requestHeader = new RequestHeader(Map.of("Host", "localhost:8080", "Connection", "keep-alive", "Cookie", sessionId, "Accept", "*/*"));
        RequestBody requestBody = new RequestBody();

        HttpRequest httpRequest = new HttpRequest(requestLine, requestHeader, requestBody);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        HttpResponse httpResponse = controller.service(httpRequest);
        new ResponseWriter(out).process(httpResponse);

        assertThat(out.toString()).contains("HTTP/1.1 200 OK");
    }

    @Test
    @DisplayName("유저목록 확인 - 로그인 실패 상태")
    void testUserList_WithFail() throws Exception {
        httpSession.setAttribute("logined", "false");
        String sessionId = String.format(JSESSIONID + "=%s; Path=/", httpSession.getId());
        SessionManagement.setSessionAttribute(httpSession);

        RequestLine requestLine = new RequestLine("GET /user/list?userId=aaaa&password=aaaa&name=aaaa HTTP/1.1");
        RequestHeader requestHeader = new RequestHeader(Map.of("Host", "localhost:8080", "Connection", "keep-alive", "Cookie", sessionId, "Accept", "*/*"));
        RequestBody requestBody = new RequestBody();

        HttpRequest httpRequest = new HttpRequest(requestLine, requestHeader, requestBody);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        HttpResponse httpResponse = controller.service(httpRequest);
        new ResponseWriter(out).process(httpResponse);

        assertThat(out.toString()).contains("HTTP/1.1 302 Found");
        assertThat(out.toString()).contains("Location: /");
    }

}