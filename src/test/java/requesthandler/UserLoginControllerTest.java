package requesthandler;

import db.DataBase;
import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webserver.request.HttpRequest;
import webserver.request.RequestHeaders;
import webserver.request.RequestLine;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;

/**
 * Created by hspark on 2019-08-06.
 */
class UserLoginControllerTest {


    @BeforeEach
    void setUp() {
        User user = new User("test", "123", "test", "test@test.com");
        DataBase.addUser(user);
    }

    @Test
    void test_로그인_성공() {
        RequestLine requestLine = RequestLine.parse("POST /users/login HTTP/1.1");
        String requestBody = "userId=test&password=123";
        RequestHeaders headers = new RequestHeaders();
        headers.add("Accept: text/html");
        HttpRequest httpRequest = HttpRequest
                .builder()
                .requestHeaders(headers)
                .requestLine(requestLine)
                .requestBody(requestBody)
                .build();

        HttpResponse httpResponse = new HttpResponse(httpRequest);

        UserLoginController userLoginController = new UserLoginController();

        userLoginController.action(httpRequest, httpResponse);

        Assertions.assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
        Assertions.assertThat(httpResponse.getResponseHeaders().getHeader("Location")).isEqualTo("/index.html");
    }

    @Test
    void test_로그인_실패() {
        RequestLine requestLine = RequestLine.parse("POST /users/login HTTP/1.1");
        String requestBody = "userId=test&password=1234";
        RequestHeaders headers = new RequestHeaders();
        headers.add("Accept: text/html");

        HttpRequest httpRequest = HttpRequest
                .builder()
                .requestHeaders(headers)
                .requestLine(requestLine)
                .requestBody(requestBody)
                .build();

        HttpResponse httpResponse = new HttpResponse(httpRequest);

        UserLoginController userLoginController = new UserLoginController();

        userLoginController.action(httpRequest, httpResponse);

        Assertions.assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
        Assertions.assertThat(httpResponse.getResponseHeaders().getHeader("Location")).isEqualTo("/user/login_failed.html");
    }
}