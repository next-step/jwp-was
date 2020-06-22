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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class UserLogoutHandlerTest {

    @AfterEach
    void tearDown() {
        DataBase.deleteAll();
    }

    @DisplayName("로그인 상태의 유저 로그아웃하기")
    @Test
    void test_logout() throws Exception {
        // given
        User user = signup();
        HttpSession sessionOfloginUser = login(user);

        RequestMessage requestMessage = RequestMessage.createWithDefaultBody(
                RequestLine.from("GET /user/logout HTTP/1.1"),
                new HttpHeaders(Arrays.asList("Cookie: JSESSIONID=" + sessionOfloginUser.getId()))
        );

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ResponseMessage responseMessage = new ResponseMessage(new DataOutputStream(output));

        // when
        UserLogoutHandler.getInstance().service(requestMessage, responseMessage);

        // then
        String result = output.toString();
        assertThat(result).contains("HTTP/1.1 302 Found\r\n", "Location: /user/login.html");
        assertThat(sessionOfloginUser.getAttribute("user")).isNull();
    }

    private HttpSession login(User user) {
        HttpSession session = HttpSessionStorage.getOrCreate("sessionID");
        session.setAttribute("user", new UserDto(user));
        return session;
    }

    private User signup() {
        User user = new User("crystal", "password", "임수정", "crystal@naver.com");
        DataBase.addUser(user);
        return user;
    }
}
