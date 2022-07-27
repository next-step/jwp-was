package webserver.handler;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.request.HttpRequest;
import webserver.request.HttpRequestParser;
import webserver.request.RequestLine;

@DisplayName("요청을 처리할 객체를 찾는다")
class RequestMappingTest {

    @DisplayName("회원가입 요청을 처리할 객체를 찾는다")
    @Test
    void find_create_user_controller() throws IOException {
        final String requestMessage = "POST /user/create HTTP/1.1\r\n" +
            "Host: localhost:8080\r\n" +
            "Connection: keep-alive\r\n" +
            "Content-Length: 34\r\n" +
            "\r\n" +
            "userId=admin&email=admin@email.com)";

        final RequestLine requestLine = parseRequestLine(requestMessage);

        final Controller actual = RequestMapping.map(requestLine);

        assertThat(actual).isInstanceOf(CreateUserController.class);
    }

    @DisplayName("로그인 요청을 처리할 객체를 찾는다")
    @Test
    void find_login_controller() throws IOException {
        final String requestMessage = "POST /user/login HTTP/1.1\r\n" +
            "Host: localhost:8080\r\n" +
            "Connection: keep-alive\r\n" +
            "Content-Length: 30\r\n" +
            "\r\n" +
            "userId=admin&password=password)";
        final RequestLine requestLine = parseRequestLine(requestMessage);

        final Controller actual = RequestMapping.map(requestLine);

        assertThat(actual).isInstanceOf(LoginController.class);
    }

    @DisplayName("사용자 목록 조회 요청을 처리할 객체를 찾는다")
    @Test
    void find_user_list_controller() throws IOException {
        final String requestMessage = "GET /user/list.html HTTP/1.1\r\n" +
            "Host: localhost:8080\r\n" +
            "Connection: keep-alive\r\n" +
            "\r\n";
        final RequestLine requestLine = parseRequestLine(requestMessage);

        final Controller actual = RequestMapping.map(requestLine);

        assertThat(actual).isInstanceOf(UserListController.class);
    }

    @DisplayName("요청을 처리할 객체를 찾지 못하면 기본 요청 처리 객체를 찾는다")
    @Test
    void find_default_controller() throws IOException {
        final String requestMessage = "GET /index.html HTTP/1.1\r\n" +
            "Host: localhost:8080\r\n" +
            "Connection: keep-alive\r\n" +
            "\r\n";
        final RequestLine requestLine = parseRequestLine(requestMessage);

        final Controller actual = RequestMapping.map(requestLine);

        assertThat(actual).isInstanceOf(DefaultController.class);
    }

    private static RequestLine parseRequestLine(final String requestMessage) throws IOException {
        final BufferedReader bufferedReader = new BufferedReader(new StringReader(requestMessage));
        final HttpRequest httpRequest = HttpRequestParser.parse(bufferedReader);

        return httpRequest.getRequestLine();
    }

}
