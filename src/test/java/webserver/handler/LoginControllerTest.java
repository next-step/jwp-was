package webserver.handler;

import static org.assertj.core.api.Assertions.assertThat;
import static webserver.request.Protocol.HTTP_1_1;
import static webserver.response.ResponseBody.EMPTY_RESPONSE_BODY;
import static webserver.response.StatusCode.FOUND;

import db.DataBase;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.request.HttpRequest;
import webserver.request.HttpRequestParser;
import webserver.response.HttpResponse;
import webserver.response.ResponseHeaders;
import webserver.response.StatusLine;
import webserver.session.HttpSession;
import webserver.session.HttpSessionContext;

class LoginControllerTest {

    private final LoginController controller = new LoginController();

    @BeforeEach
    void setUp() {
        DataBase.clear();
        DataBase.addUser(new User("admin", "pass", "administrator", "admin@email.com"));
    }

    @DisplayName("로그인 성공 후 페이지 이동 응답 객체를 반환한다")
    @Test
    void after_login_success_return_redirect_response() throws IOException {
        // given
        final HttpSession session = new HttpSession(() -> "12345");
        session.setAttribute("logined", true);
        HttpSessionContext.add(session);

        String httpRequestMessage = "POST /user/login HTTP/1.1\r\n"
            + "Host: localhost:8080\r\n"
            + "Connection: keep-alive\r\n"
            + "Content-Length: 26\r\n"
            + "Cookie: JWP_SESSION_ID=12345;\r\n"
            + "\r\n"
            + "userId=admin&password=pass\n\n";

        BufferedReader bufferedReader = getBufferedReader(httpRequestMessage);
        final HttpRequest httpRequest = HttpRequestParser.parse(bufferedReader);

        //when
        final HttpResponse actual = controller.handle(httpRequest);

        //then
        final HttpResponse expected = loginResponse("/index.html");

        assertThat(actual).isEqualTo(expected);

    }

    @DisplayName("로그인 실패 후 페이지 이동 응답 객체를 반환한다")
    @Test
    void after_login_failed_return_redirect_response() throws IOException {
        // given
        final HttpSession session = new HttpSession(() -> "12345");
        session.setAttribute("logined", false);
        HttpSessionContext.add(session);

        String httpRequestMessage = "POST /user/login HTTP/1.1\r\n"
            + "Host: localhost:8080\r\n"
            + "Connection: keep-alive\r\n"
            + "Content-Length: 26\r\n"
            + "Cookie: JWP_SESSION_ID=12345;\r\n"
            + "\r\n"
            + "userId=admin&password=wrong\n\n";

        BufferedReader bufferedReader = getBufferedReader(httpRequestMessage);
        final HttpRequest httpRequest = HttpRequestParser.parse(bufferedReader);

        //when
        final HttpResponse actual = controller.handle(httpRequest);

        //then
        final HttpResponse expected = loginResponse("/user/login_failed.html");

        assertThat(actual).isEqualTo(expected);

    }

    private static HttpResponse loginResponse(final String location) {
        final ResponseHeaders headers = new ResponseHeaders();
        headers.add("Location", location);
        return new HttpResponse(new StatusLine(HTTP_1_1, FOUND), headers, EMPTY_RESPONSE_BODY);
    }

    private BufferedReader getBufferedReader(final String httpRequestMessage) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequestMessage.getBytes());
        return new BufferedReader(new InputStreamReader(inputStream));
    }
}
