package requesthandler;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import webserver.controller.Controller;
import webserver.request.HttpRequest;
import webserver.request.RequestHeaders;
import webserver.request.RequestLine;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;

/**
 * Created by hspark on 2019-08-06.
 */
class UserListControllerTest {

    @Test
    void test_로그인이_아닐_경우() {
        Controller userListController = new UserListController();


        RequestLine requestLine = RequestLine.parse("GET /users/list HTTP/1.1");
        HttpRequest httpRequest = HttpRequest.builder().requestLine(requestLine).build();
        HttpResponse httpResponse = new HttpResponse();

        userListController.action(httpRequest, httpResponse);

        Assertions.assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
        Assertions.assertThat(httpResponse.getResponseHeaders().getHeader("Location")).isEqualTo("/index.html");
    }

    @Test
    void test_로그인일_경우() {
        Controller userListController = new UserListController();


        RequestLine requestLine = RequestLine.parse("GET /users/list HTTP/1.1");
        RequestHeaders requestHeaders = new RequestHeaders();
        requestHeaders.add("Cookie: logined=true");
        HttpRequest httpRequest = HttpRequest.builder()
                .requestLine(requestLine)
                .requestHeaders(requestHeaders)
                .build();
        HttpResponse httpResponse = new HttpResponse();

        userListController.action(httpRequest, httpResponse);

        Assertions.assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.OK);
    }
}