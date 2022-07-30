package webserver.handler;

import static org.assertj.core.api.Assertions.assertThat;
import static webserver.request.Protocol.HTTP_1_1;
import static webserver.response.ResponseBody.EMPTY_RESPONSE_BODY;
import static webserver.response.StatusCode.FOUND;
import static webserver.response.StatusCode.OK;

import db.DataBase;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.request.ContentType;
import webserver.request.HttpRequest;
import webserver.request.HttpRequestParser;
import webserver.response.HttpResponse;
import webserver.response.ResponseBody;
import webserver.response.ResponseHeaders;
import webserver.response.StatusLine;
import webserver.session.HttpSession;
import webserver.session.HttpSessionContext;

class UserListControllerTest {

    private final UserListController controller = new UserListController();

    @BeforeEach
    void setUp() {
        DataBase.clear();
        DataBase.addUser(new User("admin", "pass", "administrator", "admin@email.com"));
    }

    @DisplayName("로그인 상태에서 사용자 목록 조회시 사용자 목록 페이지 이동 응답 객체를 반환한다")
    @Test
    void after_login_access_user_list_return_ok_response() throws IOException {
        // given
        final HttpSession session = new HttpSession(() -> "12345");
        session.setAttribute("logined", true);
        HttpSessionContext.add(session);

        String httpRequestMessage = "GET /user/list.html HTTP/1.1\r\n"
            + "Host: localhost:8080\r\n"
            + "Connection: keep-alive\r\n"
            + "Cookie: JWP_SESSION_ID=12345;\r\n"
            + "\r\n";

        BufferedReader bufferedReader = getBufferedReader(httpRequestMessage);
        final HttpRequest httpRequest = HttpRequestParser.parse(bufferedReader);

        //when
        final HttpResponse actual = controller.handle(httpRequest);

        //then
        String responseBody = "\n"
            + "<tr>\n"
            + "  <td>1</td>\n"
            + "  <td>administrator</td>\n"
            + "</tr>\n"
            + "\n";

        final ResponseHeaders headers = new ResponseHeaders();
        headers.add("Content-Type", ContentType.HTML.getMediaType());
        headers.add("Content-Length", String.valueOf(responseBody.getBytes().length));
        final HttpResponse expected = new HttpResponse(new StatusLine(HTTP_1_1, OK), headers, new ResponseBody(responseBody));

        assertThat(actual).isEqualTo(expected);

    }

    @DisplayName("비로그인 상태에서 사용자 목록 조회시 로그인 페이지 이동 응답 객체를 반환한다")
    @Test
    void access_user_list_without_login_return_redirect_response() throws IOException {
        // given
        String httpRequestMessage = "GET /user/list.html HTTP/1.1\r\n"
            + "Host: localhost:8080\r\n"
            + "Connection: keep-alive\r\n"
            + "\r\n";

        BufferedReader bufferedReader = getBufferedReader(httpRequestMessage);
        final HttpRequest httpRequest = HttpRequestParser.parse(bufferedReader);

        //when
        final HttpResponse actual = controller.handle(httpRequest);

        //then
        final ResponseHeaders headers = new ResponseHeaders();
        headers.add("Location", "/user/login.html");
        final HttpResponse expected = new HttpResponse(new StatusLine(HTTP_1_1, FOUND), headers, EMPTY_RESPONSE_BODY);

        assertThat(actual).isEqualTo(expected);

    }

    private BufferedReader getBufferedReader(final String httpRequestMessage) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequestMessage.getBytes());
        return new BufferedReader(new InputStreamReader(inputStream));
    }

}
