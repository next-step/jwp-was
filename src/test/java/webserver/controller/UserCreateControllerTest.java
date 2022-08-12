package webserver.controller;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import webserver.http.header.Header;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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
                () -> assertThat(httpResponse.isHeaderValueEqual(ResponseHeader.LOCATION, "/index.html")).isTrue(),
                () -> assertThat(DataBase.findUserById(httpRequest.getParam("userId"))).isEqualTo(user)
        );
    }

    @ParameterizedTest
    @DisplayName("POST 요청 이외의 메서드는 Not Found 를 반환한다.")
    @CsvSource(value = {
            "GET, PUT, DELETE, PATCH"
    })
    void throw_exception_exceptGetMethod(Method method) throws IOException, URISyntaxException {
        HttpRequest httpRequest = new HttpRequest(new RequestLine(method, new Path("/user/create", new QueryString()), Protocol.ofHttpV11()), new Header(), new QueryString());
        assertThat(controller.process(httpRequest)).isEqualTo(HttpResponse.notFound());
    }

    @ParameterizedTest
    @DisplayName("해당 요청에 대한 Mapping 이 일치하는지 확인한다.")
    @CsvSource(value = {
            "/user/create, true",
            "/user/creates, false",
    })
    void isMatchRequest(String path, boolean trueOrFalse) {
        HttpRequest httpRequest = new HttpRequest(new RequestLine(Method.GET, new Path(path, new QueryString()), Protocol.ofHttpV11()), new Header(), new QueryString());
        assertThat(controller.isMatchPath(httpRequest)).isEqualTo(trueOrFalse);
    }
}