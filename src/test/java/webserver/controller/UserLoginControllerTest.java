package webserver.controller;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import webserver.http.HttpSession;
import webserver.http.header.Header;
import webserver.http.header.HeaderValue;
import webserver.http.header.type.ResponseHeader;
import webserver.http.request.HttpRequest;
import webserver.http.request.requestline.Method;
import webserver.http.request.requestline.Path;
import webserver.http.request.requestline.Protocol;
import webserver.http.request.requestline.QueryString;
import webserver.http.request.requestline.RequestLine;
import webserver.http.response.HttpResponse;
import webserver.http.response.statusline.StatusCode;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class UserLoginControllerTest {
    private static Controller controller;

    @BeforeAll
    static void setUp() {
        controller = new UserLoginController();
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void loginSuccess() throws IOException, URISyntaxException {
        // given
        User user = new User("test_id", "test_password", "test_name", "test@test.com");
        DataBase.addUser(user);
        HttpRequest httpRequest = RequestTestUtil.readTestRequest("login.txt");
        String sessionId = httpRequest.getSessionId();

        HttpSession expectedSession = new HttpSession(sessionId, Map.of("user", user));

        // when
        HttpResponse httpResponse = controller.process(httpRequest);
        HttpSession actualSession = httpRequest.getSession();

        // then
        assertAll(
                () -> assertThat(httpResponse.isStatusCodeEqual(StatusCode.FOUND)).isTrue(),
                () -> assertThat(httpResponse.isHeaderValueEqual(ResponseHeader.SET_COOKIE, String.format(HeaderValue.JSESSION_ID, sessionId))).isTrue(),
                () -> assertThat(httpResponse.isHeaderValueEqual(ResponseHeader.LOCATION, "/index.html")).isTrue(),
                () -> assertThat(expectedSession.getAttribute("user")).isEqualTo(actualSession.getAttribute("user"))
        );
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    void loginFail() throws IOException, URISyntaxException {
        // given
        DataBase.addUser(new User("test_id", "test_password_fail", "test_name", "test@test.com"));
        HttpRequest httpRequest = RequestTestUtil.readTestRequest("login.txt");

        // when
        HttpResponse httpResponse = controller.process(httpRequest);

        // then
        assertAll(
                () -> assertThat(httpResponse.isStatusCodeEqual(StatusCode.FOUND)).isTrue()
        );
    }

    @ParameterizedTest
    @DisplayName("POST 요청 이외의 메서드는 Not Found 를 반환한다.")
    @CsvSource(value = {
            "GET, PUT, DELETE, PATCH"
    })
    void throw_exception_exceptGetMethod(Method method) throws IOException, URISyntaxException {
        HttpRequest httpRequest = new HttpRequest(new RequestLine(method, new Path("/user/login", new QueryString()), Protocol.ofHttpV11()), new Header(), new QueryString());
        assertThat(controller.process(httpRequest)).isEqualTo(HttpResponse.notFound());
    }

    @ParameterizedTest
    @DisplayName("해당 요청에 대한 Mapping 이 일치하는지 확인한다.")
    @CsvSource(value = {
            "/user/login, true",
            "/user/logins, false",
    })
    void isMatchRequest(String path, boolean trueOrFalse) {
        HttpRequest httpRequest = new HttpRequest(new RequestLine(Method.GET, new Path(path, new QueryString()), Protocol.ofHttpV11()), new Header(), new QueryString());
        assertThat(controller.isMatchPath(httpRequest)).isEqualTo(trueOrFalse);
    }
}