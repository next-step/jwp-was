package webserver.handler.custom;

import http.request.Headers;
import http.request.Request;
import http.request.RequestBody;
import http.request.RequestLine;
import http.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static http.response.HttpStatus.FOUND;
import static org.assertj.core.api.Assertions.assertThat;

public class UserListHandlerTest {
    @DisplayName("로그인 한 상태 -> User 목록 페이지 출력")
    @Test
    void workWhenLoginSuccess() throws IOException, URISyntaxException {
        //given
        String requestLine = "GET /user/list HTTP/1.1";
        Headers headers = createRequestHeader("logined=true; Path=/");
        String body = "";
        Request request = new Request(new RequestLine(requestLine), headers, new RequestBody(body));
        UserListHandler handler = new UserListHandler("/user/list");

        //when
        Response response = handler.work(request);

        //then
        assertThat(response.getStatus()).isEqualTo(FOUND);
        assertThat(response.getBody())
                .isNotEqualTo(FileIoUtils.loadFileFromClasspath("./templates/user/list.html"));
    }

    @DisplayName("로그인 되지 않은 상태 -> 로그인 페이지 출력")
    @Test
    void workWhenLoginFails() throws IOException {
        //given
        String requestLine = "GET /user/list HTTP/1.1";
        Headers headers = createRequestHeader("logined=false");
        String body = "";
        Request request = new Request(new RequestLine(requestLine), headers, new RequestBody(body));
        UserListHandler handler = new UserListHandler("/user/list");

        //when
        Response response = handler.work(request);

        //then
        assertThat(response.getHeaderByKey("Location")).isEqualTo("/user/login_failed.html");
    }

    private Headers createRequestHeader(String cookie) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Cookie", cookie);
        return new Headers(headers);
    }
}
