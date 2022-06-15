package service;

import java.io.IOException;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.request.Request;
import webserver.request.RequestBody;
import webserver.request.RequestHeader;
import webserver.request.RequestLine;
import webserver.response.Response;

import static org.assertj.core.api.Assertions.assertThat;

class ListUserServiceTest {

    @DisplayName("로그인이 되어 있지 않으면, login.html 로 Redirect 된다.")
    @Test
    void notLoggedIn() throws IOException {
        // given
        RequestLine requestLine = RequestLine.from(
                "GET /user/list.html HTTP/1.1"
        );
        RequestHeader requestHeader = RequestHeader.from(Arrays.asList(
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
                "Cookie: loggedIn=false"
        ));
        RequestBody requestBody = RequestBody.from("");
        Request request = new Request(requestLine, requestHeader, requestBody);

        // when
        Response response = ListUserService.doGet(request);
        String actual = response.toString();

        //then
        String expected = "HTTP/1.1 302 Found \r\n"
                + "Location: /user/login.html \r\n"
                + "\r\n";
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("로그인이 되어 있다면, 200 을 응답한다.")
    @Test
    void success() throws IOException {
        // given
        RequestLine requestLine = RequestLine.from(
                "GET /user/list.html HTTP/1.1"
        );
        RequestHeader requestHeader = RequestHeader.from(Arrays.asList(
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
                "Cookie: loggedIn=true"
        ));
        RequestBody requestBody = RequestBody.from("");
        Request request = new Request(requestLine, requestHeader, requestBody);

        // when
        Response response = ListUserService.doGet(request);
        String actual = response.toString();

        //then
        assertThat(actual.contains("200 OK")).isTrue();
    }
}
