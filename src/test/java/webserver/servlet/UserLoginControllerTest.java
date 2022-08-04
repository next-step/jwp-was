package webserver.servlet;

import static model.UserTest.TEST_ID;
import static model.UserTest.TEST_PW;
import static org.assertj.core.api.Assertions.assertThat;

import db.DataBase;
import model.UserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webserver.domain.HttpHeader;
import webserver.enums.HttpStatus;
import webserver.request.HttpRequest;
import webserver.request.HttpRequestBody;
import webserver.request.HttpRequestHeader;
import webserver.request.RequestLine;
import webserver.response.HttpResponse;

class UserLoginControllerTest {

    private Controller controller;

    @BeforeEach
    void setUp() {
        controller = new UserLoginController();

        DataBase.deleteAll();
        DataBase.addUser(UserTest.TEST_USER);
    }

    @Test
    void loginFailTest() {
        RequestLine requestLine = RequestLine.of("POST /user/login HTTP/1.1");
        HttpRequestHeader httpHeader = HttpRequestHeader.createEmpty();
        httpHeader.putHeader("Host", "localhost:8080");
        httpHeader.putHeader("Connection", "keep-alive");
        httpHeader.putHeader("Content-Length", "33");
        httpHeader.putHeader("Content-Type", "application/x-www-form-urlencoded");
        httpHeader.putHeader("Accept", "*/*");
        HttpRequestBody httpBody = HttpRequestBody.of("userId=" + TEST_ID + "&password=invalidPassword");

        HttpRequest httpRequest = new HttpRequest(requestLine, httpHeader, httpBody);
        HttpResponse httpResponse = new HttpResponse();

        controller.service(httpRequest, httpResponse);

        assertThat(httpResponse.getStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(httpResponse.getHeader().getHeader("Location")).isEqualTo("/user/login_failed.html");
    }

    @Test
    void loginOkTest() {
        RequestLine requestLine = RequestLine.of("POST /user/login HTTP/1.1");
        HttpRequestHeader httpHeader = HttpRequestHeader.createEmpty();
        httpHeader.putHeader("Host", "localhost:8080");
        httpHeader.putHeader("Connection", "keep-alive");
        httpHeader.putHeader("Content-Length", "33");
        httpHeader.putHeader("Content-Type", "application/x-www-form-urlencoded");
        httpHeader.putHeader("Accept", "*/*");
        HttpRequestBody httpBody = HttpRequestBody.of("userId=" + TEST_ID + "&password=" + TEST_PW);

        HttpRequest httpRequest = new HttpRequest(requestLine, httpHeader, httpBody);
        HttpResponse httpResponse = new HttpResponse();

        controller.service(httpRequest, httpResponse);

        assertThat(httpResponse.getStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(httpResponse.getHeader().getHeader(HttpHeader.SET_COOKIE)).isNotEmpty();
//        assertThat(httpResponse.getHeader().getHeader(HttpHeader.SET_COOKIE)).isEqualTo("logined=true; Path=/");
        assertThat(httpResponse.getHeader().getHeader(HttpHeader.LOCATION)).contains("index.html");
    }

}
