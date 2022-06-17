package service;

import db.DataBase;
import java.util.Arrays;
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

class LoginServiceTest {

    @BeforeEach
    void setUp() {
        DataBase.clear();
    }

    private static final User user = new User(
            "javajigi",
            "P@ssw0rD",
            "JaeSung",
            "javajigi@slipp.net"
    );

    @DisplayName("GET 로그인 요청이 성공하면, 쿠키 값이 loggedIn=true 가 되고, index.html 로 Redirect 한다.")
    @Test
    void doGetLoginSuccess() {
        // given
        DataBase.addUser(user);
        RequestLine requestLine = RequestLine.from(
                "GET /user/login?userId=javajigi&password=P@ssw0rD HTTP/1.1"
        );
        RequestHeader requestHeader = RequestHeader.from(Arrays.asList(
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"
        ));
        RequestBody requestBody = RequestBody.from("");
        Request request = new Request(requestLine, requestHeader, requestBody);

        // when
        Response response = LoginService.doGet(request);
        String actual = response.toString();

        //then
        String expected = "HTTP/1.1 302 Found \r\n"
                + "Location: /index.html \r\n"
                + "Set-Cookie: loggedIn=true \r\n"
                + "Content-Length: 0 \r\n"
                + "\r\n";
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("GET 로그인 요청이 실패하면, 쿠키 값이 loggedIn=false 가 되고, login_failed.html 로 Redirect 한다.")
    @Test
    void doGetLoginFailed() {
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
        Response response = LoginService.doGet(request);
        String actual = response.toString();

        //then
        String expected = "HTTP/1.1 302 Found \r\n"
                + "Location: /login_failed.html \r\n"
                + "Set-Cookie: loggedIn=false \r\n"
                + "Content-Length: 0 \r\n"
                + "\r\n";
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("POST 로그인 요청이 성공하면, 쿠키 값이 loggedIn=true 가 되고, index.html 로 Redirect 한다.")
    @Test
    void doPostLoginSuccess() {
        // given
        DataBase.addUser(user);
        RequestLine requestLine = RequestLine.from("POST /user/login HTTP/1.1");
        RequestHeader requestHeader = RequestHeader.from(Arrays.asList(
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
                "Content-Length: 0"
        ));
        RequestBody requestBody = RequestBody.from("userId=javajigi&password=P@ssw0rD");
        Request request = new Request(requestLine, requestHeader, requestBody);

        // when
        Response response = LoginService.doPost(request);
        String actual = response.toString();

        //then
        String expected = "HTTP/1.1 302 Found \r\n"
                + "Location: /index.html \r\n"
                + "Set-Cookie: loggedIn=true \r\n"
                + "Content-Length: 0 \r\n"
                + "\r\n";
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("POST 로그인 요청이 실패하면, 쿠키 값이 loggedIn=false 가 되고, login_failed.html 로 Redirect 한다.")
    @Test
    void doPostLoginFailed() {
        // given
        String body = "userId=id&password=pw";
        RequestLine requestLine = RequestLine.from("POST /user/login HTTP/1.1");
        RequestHeader requestHeader = RequestHeader.from(Arrays.asList(
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
                "Content-Length: " + body.getBytes().length
        ));
        RequestBody requestBody = RequestBody.from(body);
        Request request = new Request(requestLine, requestHeader, requestBody);

        // when
        Response response = LoginService.doPost(request);
        String actual = response.toString();

        //then
        String expected = "HTTP/1.1 302 Found \r\n"
                + "Location: /login_failed.html \r\n"
                + "Set-Cookie: loggedIn=false \r\n"
                + "Content-Length: 0 \r\n"
                + "\r\n";
        assertThat(actual).isEqualTo(expected);
    }
}
