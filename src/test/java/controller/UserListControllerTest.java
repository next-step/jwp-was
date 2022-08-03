package controller;

import model.*;
import org.junit.jupiter.api.Test;
import webserver.RequestLine;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserListControllerTest {

    @Test
    void 로그인_쿠키_존재시_정상응답() throws Exception {

        final HttpRequest httpRequest = createHttpRequest(true);
        final UserListController controller = new UserListController();
        final HttpResponse response = controller.process(httpRequest);

        assertThat(response.getMessages().get(0)).isEqualTo("HTTP/1.1 200 OK \r\n");
    }

    @Test
    void 로그인_쿠키_없을경우_리다이렉트() throws Exception {

        final HttpRequest httpRequest = createHttpRequest(false);
        final UserListController controller = new UserListController();
        final HttpResponse response = controller.process(httpRequest);

        assertThat(response.getMessages().get(0)).isEqualTo("HTTP/1.1 302 OK \r\n");
    }

    private User createUser() throws UnsupportedEncodingException {
        final String data = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        final RequestBody body = new RequestBody(data);

        return User.createUser(body);
    }

    private HttpRequest createHttpRequest(boolean cookie) throws UnsupportedEncodingException {
        final String data = "GET /user/list HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n" +
                "Cookie: logined=" + cookie;

        return new HttpRequest(new HttpHeader(headers(cookie)), "");
    }

    private List<String> headers(boolean cookie) {
        return Arrays.asList( "GET /user/list HTTP/1.1\n",
                "Cookie: logined=" + cookie);
    }
}
