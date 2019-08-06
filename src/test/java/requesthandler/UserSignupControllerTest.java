package requesthandler;

import db.DataBase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import webserver.controller.Controller;
import webserver.request.HttpRequest;
import webserver.request.RequestLine;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by hspark on 2019-08-06.
 */
class UserSignupControllerTest {

    @Test
    void test_signup() {
        RequestLine requestLine = RequestLine.parse("GET /users?userId=test&password=test&email=test@test.com&name=test HTTP/1.1");
        HttpRequest httpRequest = HttpRequest.builder().requestLine(requestLine).build();
        HttpResponse httpResponse = new HttpResponse();

        Controller userSignupController = new UserSignupController();
        userSignupController.action(httpRequest, httpResponse);

        Assertions.assertThat(DataBase.findUserById("test").getPassword()).isEqualTo("test");
        Assertions.assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
        Assertions.assertThat(httpResponse.getResponseHeaders().getHeader("Location")).isEqualTo("/index.html");
    }
}