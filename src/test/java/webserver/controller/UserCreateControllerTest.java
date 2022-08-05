package webserver.controller;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import webserver.http.Header;
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
import static org.junit.jupiter.api.Assertions.*;

class UserCreateControllerTest {
    private static Controller controller;

    @BeforeAll
    static void setUp() {
        controller = new UserCreateController();
    }

    @Test
    @DisplayName("회원가입 테스트")
    void userCreateSuccess() throws IOException, URISyntaxException {
        // given
        HttpRequest httpRequest = RequestTestUtil.readTestRequest("create.txt");
        User user = new User("test_id", "test_password", "test_name", "test@test.com");

        // when
        HttpResponse httpResponse = controller.process(httpRequest);

        // then
        assertAll(
                () -> assertThat(httpResponse.isStatusCodeEqual(StatusCode.FOUND)).isTrue(),
                () -> assertThat(httpResponse.isHeaderValueEqual("Location", "/index.html")).isTrue(),
                () -> assertThat(DataBase.findUserById(httpRequest.getParam("userId"))).isEqualTo(user)
        );
    }

    @ParameterizedTest
    @DisplayName("해당 요청에 대한 Mapping 이 일치하는지 확인한다.")
    @CsvSource(value = {
            "GET, /user/create, false",
            "GET, /user/creates, false",
            "POST, /user/create, true",
            "POST, /user/creates, false"
    })
    void isMatchRequest(Method method, String path, boolean trueOrFalse) {
        HttpRequest httpRequest = new HttpRequest(new RequestLine(method, new Path(path, new QueryString()), new Protocol(ProtocolType.HTTP, Version.ONE_ONE)), new Header(), new QueryString());
        assertThat(controller.isMatchRequest(httpRequest)).isEqualTo(trueOrFalse);
    }
}