package handler;

import db.DataBase;
import dto.UserDto;
import http.common.HttpHeaders;
import http.common.HttpSession;
import http.common.HttpSessionStorage;
import http.request.RequestLine;
import http.request.RequestMessage;
import http.response.ResponseMessage;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class UserListHandlerTest {

    private static final Logger logger = LoggerFactory.getLogger(UserListHandlerTest.class);

    User first;
    User second;

    @BeforeEach
    void setUp() {
        first = new User("first", "password", "첫째", "first@naver.com");
        second = new User("second", "password", "둘째", "second@gmail.com");

        DataBase.addUser(first);
        DataBase.addUser(second);
    }

    @AfterEach
    void tearDown() {
        DataBase.deleteAll();
    }

    @DisplayName("쿠키의 세션이 서버에 등록되어 있으면 /user/list 요청 시 유저목록 보여주기")
    @Test
    void test_showUserList_when_cookie_has_login() throws IOException {
        // given
        HttpSession session = HttpSessionStorage.getOrCreate("sessionID");
        session.setAttribute("user", new UserDto(new User("crystal", "password", "sujung", "crystal@namver.com")));

        RequestMessage requestMessage = RequestMessage.createWithDefaultBody(
                RequestLine.from("GET /user/list HTTP/1.1"),
                new HttpHeaders(Arrays.asList("Cookie: JSESSIONID=" + session.getId()))
        );

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ResponseMessage responseMessage = new ResponseMessage(new DataOutputStream(output));
        // when
        UserListHandler.getInstance().service(requestMessage, responseMessage);
        // then
        String result = output.toString();
        assertThat(result).contains(MessageFormat.format("<td>{0}</td> <td>{1}</td> <td>{2}</td>", first.getUserId(), first.getName(), first.getEmail()));
        assertThat(result).contains(MessageFormat.format("<td>{0}</td> <td>{1}</td> <td>{2}</td>", second.getUserId(), second.getName(), second.getEmail()));
    }

    @DisplayName("쿠키에 세션이 없으면 /user/list 요청 시 로그인 페이지 반환")
    @Test
    void test_showLoginPage_when_cookie_has_not_login() throws IOException, URISyntaxException {
        // given
        RequestMessage requestMessage = RequestMessage.createWithDefaultBody(
                RequestLine.from("GET /user/list HTTP/1.1"),
                new HttpHeaders(Collections.emptyList())
        );

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ResponseMessage responseMessage = new ResponseMessage(new DataOutputStream(output));

        byte[] loginTemplate = FileIoUtils.loadFileFromClasspath("./templates/user/login.html");
        // when
        UserListHandler.getInstance().service(requestMessage, responseMessage);
        // then
        byte[] result = output.toByteArray();
        assertThat(result).contains(loginTemplate);
    }

    @DisplayName("쿠키에 세션이 있으나 등록되지 않았으면 /user/list 요청 시 로그인 페이지 반환")
    @Test
    void test_showLoginPage_when_session_not_registered() throws IOException, URISyntaxException {
        // given
        RequestMessage requestMessage = RequestMessage.createWithDefaultBody(
                RequestLine.from("GET /user/list HTTP/1.1"),
                new HttpHeaders(Arrays.asList("Cookie: JSESSIONID=등록되지 않은 세션아이디"))
        );

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ResponseMessage responseMessage = new ResponseMessage(new DataOutputStream(output));

        byte[] loginTemplate = FileIoUtils.loadFileFromClasspath("./templates/user/login.html");
        // when
        UserListHandler.getInstance().service(requestMessage, responseMessage);
        // then
        byte[] result = output.toByteArray();
        assertThat(result).contains(loginTemplate);
    }
}