package controller;

import db.DataBase;
import java.io.IOException;
import java.util.Arrays;
import javax.servlet.http.HttpSession;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.request.Request;
import webserver.request.RequestBody;
import webserver.request.RequestHeader;
import webserver.request.RequestLine;
import webserver.response.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class GetUserLoginControllerTest {

    @BeforeEach
    void setUp() {
        DataBase.clear();
    }

    @DisplayName("GET 로그인 요청이 성공하면, 쿠키 값이 loggedIn=true 가 되고, index.html 로 Redirect 한다.")
    @Test
    void doGetLoginSuccess() throws IOException {
        // given
        DataBase.addUser(new User(
                "javajigi",
                "P@ssw0rD",
                "JaeSung",
                "javajigi@slipp.net"
        ));
        RequestLine requestLine = RequestLine.from(
                "GET /user/login?userId=javajigi&password=P@ssw0rD HTTP/1.1"
        );
        RequestHeader requestHeader = RequestHeader.from(Arrays.asList(
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"
        ));
        RequestBody requestBody = RequestBody.from("");
        Request request = new Request(requestLine, requestHeader, requestBody);

        // when
        Response response = Container.handle(request);
        String actual = response.toString();
        HttpSession httpSession = response.getSession();

        //then
        String expected = "HTTP/1.1 302 Found \r\n"
                + "Location: /index.html \r\n";
        assertAll(
                () -> assertThat(actual).startsWith(expected),
                () -> assertThat(httpSession.getAttribute("loggedIn")).isEqualTo(true)
        );
    }

    @DisplayName("GET 로그인 요청이 실패하면, 쿠키 값이 loggedIn=false 가 되고, login_failed.html 로 Redirect 한다.")
    @Test
    void doGetLoginFailed() throws IOException {
        // given
        RequestLine requestLine = RequestLine.from(
                "GET /user/login?userId=id&password=pw HTTP/1.1"
        );
        RequestHeader requestHeader = RequestHeader.from(Arrays.asList(
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"
        ));
        RequestBody requestBody = RequestBody.from("");
        Request request = new Request(requestLine, requestHeader, requestBody);

        // when
        Response response = Container.handle(request);
        String actual = response.toString();
        HttpSession httpSession = response.getSession();

        //then
        String expected = "HTTP/1.1 302 Found \r\n"
                + "Location: /login_failed.html \r\n";
        assertAll(
                () -> assertThat(actual).startsWith(expected),
                () -> assertThat(httpSession.getAttribute("loggedIn")).isEqualTo(false)
        );
    }
}
