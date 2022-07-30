package webserver.servlet;

import static model.UserTest.TEST_ID;
import static model.UserTest.TEST_PW;
import static org.assertj.core.api.Assertions.assertThat;

import db.DataBase;
import model.UserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webserver.enums.StatusCode;
import webserver.request.HttpRequestBody;
import webserver.request.HttpRequestHeader;
import webserver.request.HttpRequest;
import webserver.request.RequestLine;
import webserver.response.HttpResponse;

class UserLoginServletTest {
    private Servlet servlet;

    @BeforeEach
    void setUp() {
        servlet = new UserLoginServlet();

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
        HttpRequestBody httpBody = HttpRequestBody.of("userId="+TEST_ID+"&password=invalidPassword");

        HttpRequest httpRequest = new HttpRequest(requestLine, httpHeader, httpBody);
        HttpResponse httpResponse = new HttpResponse();

        servlet.serve(httpRequest, httpResponse);

        assertThat(httpResponse.getStatus()).isEqualTo(StatusCode.FOUND);
        assertThat(httpResponse.getHeader().getHeader("Location")).isEqualTo("http://localhost:8080/user/login_failed.html");
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
        HttpRequestBody httpBody = HttpRequestBody.of("userId="+TEST_ID+"&password="+TEST_PW);

        HttpRequest httpRequest = new HttpRequest(requestLine, httpHeader, httpBody);
        HttpResponse httpResponse = new HttpResponse();

        servlet.serve(httpRequest, httpResponse);

        assertThat(httpResponse.getStatus()).isEqualTo(StatusCode.FOUND);
        assertThat(httpResponse.getHeader().getHeader("Set-Cookie")).isEqualTo("logined=true; Path=/");
        assertThat(httpResponse.getHeader().getHeader("Location")).contains("index.html");
    }

}
