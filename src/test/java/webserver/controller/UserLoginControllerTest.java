package webserver.controller;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import webserver.http.Header;
import webserver.http.HeaderKey;
import webserver.http.HeaderValue;
import webserver.http.request.HttpRequest;
import webserver.http.request.requestline.Method;
import webserver.http.request.requestline.Path;
import webserver.http.request.requestline.Protocol;
import webserver.http.request.requestline.ProtocolType;
import webserver.http.request.requestline.QueryString;
import webserver.http.request.requestline.RequestLine;
import webserver.http.request.requestline.Version;
import webserver.http.response.HttpResponse;
import webserver.http.response.statusline.StatusCode;

import java.io.IOException;
import java.net.URISyntaxException;

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
        DataBase.addUser(new User("test_id", "test_password", "test_name", "test@test.com"));
        HttpRequest httpRequest = RequestTestUtil.readTestRequest("login.txt");

        // when
        HttpResponse httpResponse = controller.process(httpRequest);

        // then
        assertAll(
                () -> assertThat(httpResponse.isStatusCodeEqual(StatusCode.FOUND)).isTrue(),
                () -> assertThat(httpResponse.isHeaderValueEqual(HeaderKey.SET_COOKIE, HeaderValue.LOGINED_TRUE_ALL_PATH)).isTrue(),
                () -> assertThat(httpResponse.isHeaderValueEqual(HeaderKey.LOCATION, "/index.html")).isTrue()
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
                () -> assertThat(httpResponse.isStatusCodeEqual(StatusCode.FOUND)).isTrue(),
                () -> assertThat(httpResponse.isHeaderValueEqual(HeaderKey.SET_COOKIE, HeaderValue.LOGINED_FALSE_ALL_PATH)).isTrue()
        );
    }

    @ParameterizedTest
    @DisplayName("해당 요청에 대한 Mapping 이 일치하는지 확인한다.")
    @CsvSource(value = {
            "GET, /user/login, false",
            "GET, /user/logins, false",
            "POST, /user/login, true",
            "POST, /user/logins, false"
    })
    void isMatchRequest(Method method, String path, boolean trueOrFalse) {
        HttpRequest httpRequest = new HttpRequest(new RequestLine(method, new Path(path, new QueryString()), new Protocol(ProtocolType.HTTP, Version.ONE_ONE)), new Header(), new QueryString());
        assertThat(controller.isMatchRequest(httpRequest)).isEqualTo(trueOrFalse);
    }
}