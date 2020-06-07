package webserver.handler.custom;

import http.request.Headers;
import http.request.Request;
import http.request.RequestBody;
import http.request.RequestLine;
import http.response.Response;
import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class UserListHandlerTest {
    @DisplayName("로그인 성공 시 User 목록 출력, 로그인 실패 시 로그인 실패 페이지 출력")
    @ParameterizedTest
    @CsvSource(value = {"logined=true:/user/list.html", "logined=false:/user/login_failed.html"}, delimiter = ':')
    void workWhenLoginSuccess(String cookie, String location) throws IOException {
        //given
        String requestLine = "GET /user/list HTTP/1.1";
        Headers headers = createRequestHeader(cookie);
        String body = "";
        Request request = new Request(new RequestLine(requestLine), headers, new RequestBody(body));
        UserListHandler handler = new UserListHandler("/user/list");

        //when
        Response response = handler.work(request);

        //then
        assertThat(response.getHeaderByKey("Location")).isEqualTo(location);
    }

    private Headers createRequestHeader(String cookie){
        Map<String, String> headers = new HashMap<>();
        headers.put("Cookie", cookie);
        return new Headers(headers);
    }
}
