package webserver.servlet;

import static model.UserTest.TEST_ID;
import static model.UserTest.TEST_PW;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import db.DataBase;
import model.UserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webserver.enums.StatusCode;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

class UserLoginServletTest {
    private Servlet servlet;

    @BeforeEach
    void setUp() {
        servlet = new UserLoginServlet();

        DataBase.deleteAll();
        DataBase.addUser(UserTest.TEST_USER);
    }

    /**
     * 아이디와 비밀번호가 같은지를 확인해
     * 로그인이 성공하면 응답 header의 Set-Cookie 값을 logined=true,
     * 로그인이 실패할 경우 Set-Cookie 값을 logined=false로 설정한다.
     *
     * Set-Cookie 설정시 모든 요청에 대해 Cookie 처리가 가능하도록 Path 설정 값을 /(Path=/)로 설정한다.
     *
     * 응답 header에 Set-Cookie값을 설정한 후 요청 header에 Cookie이 전달되는지 확인한다.
     */
    @Test
    void loginFailTest() {
        HttpRequest httpRequest = new HttpRequest("POST /user/login HTTP/1.1");
        httpRequest.addHeader("Host: localhost:8080");
        httpRequest.addHeader("Connection: keep-alive");
        httpRequest.addHeader("Content-Length: 33");
        httpRequest.addHeader("Content-Type: application/x-www-form-urlencoded");
        httpRequest.addHeader("Accept: */*");
        httpRequest.setBody("userId="+TEST_ID+"&password=invalidPassword");
        HttpResponse httpResponse = new HttpResponse();

        servlet.serve(httpRequest, httpResponse);

        assertThat(httpResponse.getStatus()).isEqualTo(StatusCode.FOUND);
        assertThat(httpResponse.getHeader().getHeader("Location")).isEqualTo("http://localhost:8080/user/login_failed.html");
    }

    @Test
    void loginOkTest() {
        HttpRequest httpRequest = new HttpRequest("POST /user/login HTTP/1.1");
        httpRequest.addHeader("Host: localhost:8080");
        httpRequest.addHeader("Connection: keep-alive");
        httpRequest.addHeader("Content-Length: 33");
        httpRequest.addHeader("Content-Type: application/x-www-form-urlencoded");
        httpRequest.addHeader("Accept: */*");
        httpRequest.setBody("userId="+TEST_ID+"&password="+TEST_PW);
        HttpResponse httpResponse = new HttpResponse();

        servlet.serve(httpRequest, httpResponse);

        assertThat(httpResponse.getStatus()).isEqualTo(StatusCode.OK);
        assertThat(httpResponse.getHeader().getHeader("Set-Cookie")).isEqualTo("logined=true; Path=/");
    }

}
