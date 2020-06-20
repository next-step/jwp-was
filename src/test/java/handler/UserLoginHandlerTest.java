package handler;

import db.DataBase;
import http.common.HttpHeaders;
import http.request.RequestLine;
import http.request.RequestMessage;
import http.response.ResponseMessage;
import model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class UserLoginHandlerTest {

    @BeforeAll
    static void beforeAll() {
        User user = new User("crystal", "password", "임수정", "crystal@naver.com");
        DataBase.addUser(user);
    }

    @AfterAll
    static void afterAll() {
        DataBase.deleteAll();
    }

    @DisplayName("로그인 성공")
    @Test
    void test_login_success() throws IOException {
        // given
        RequestMessage requestMessage = RequestMessage.create(
                RequestLine.from("POST /user/login HTTP/1.1"),
                new HttpHeaders(Collections.emptyList()),
                "userId=crystal&password=password"
        );

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ResponseMessage responseMessage = new ResponseMessage(new DataOutputStream(output));
        // when
        UserLoginHandler.getInstance().service(requestMessage, responseMessage);
        // then
        String result = output.toString();
        assertThat(result).startsWith("HTTP/1.1 302 Found\r\n").contains("Location: /index.html\r\n",
                "Set-Cookie: logined=true; Path=/\r\n").endsWith("\r\n");
    }

    @DisplayName("비밀번호 불일치로 로그인 실패")
    @Test
    void test_login_fail_password_not_same() throws IOException, URISyntaxException {
        // given
        RequestMessage requestMessage = RequestMessage.create(
                RequestLine.from("POST /user/login HTTP/1.1"),
                new HttpHeaders(Collections.emptyList()),
                "userId=crystal&password=popo"
        );

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ResponseMessage responseMessage = new ResponseMessage(new DataOutputStream(output));
        byte[] loginFailTemplate = FileIoUtils.loadFileFromClasspath("./templates/user/login_failed.html");
        // when
        UserLoginHandler.getInstance().service(requestMessage, responseMessage);
        // then
        byte[] result = output.toByteArray();
        assertThat(result).contains(loginFailTemplate);
    }

    @DisplayName("미가입 유저로 로그인 실패")
    @Test
    void test_login_fail_not_registred_user() throws IOException, URISyntaxException {
        // given
        RequestMessage requestMessage = RequestMessage.create(
                RequestLine.from("POST /user/login HTTP/1.1"),
                new HttpHeaders(Collections.emptyList()),
                "userId=keke&password=popo"
        );

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ResponseMessage responseMessage = new ResponseMessage(new DataOutputStream(output));
        byte[] loginFailTemplate = FileIoUtils.loadFileFromClasspath("./templates/user/login_failed.html");
        // when
        UserLoginHandler.getInstance().service(requestMessage, responseMessage);
        // then
        byte[] result = output.toByteArray();
        assertThat(result).contains(loginFailTemplate);
    }
}