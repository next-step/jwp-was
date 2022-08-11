package webserver.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
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
import webserver.http.request.requestline.QueryString;
import webserver.http.request.requestline.RequestLine;
import webserver.http.response.HttpResponse;
import webserver.http.response.statusline.StatusCode;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class UserListControllerTest {
    private static Controller controller;

    @BeforeAll
    static void setUp() {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        controller = new UserListController(new Handlebars(loader));
    }

    @Test
    @DisplayName("사용자 목록 조회 성공 테스트")
    void userListSuccess() throws IOException, URISyntaxException {
        // given
        DataBase.addUser(new User("test_id", "test_password", "test_name", "test@test.com"));
        HttpRequest httpRequest = RequestTestUtil.readTestRequest("user_list_success.txt");

        // when
        HttpResponse httpResponse = controller.process(httpRequest);
        String body = new String(httpResponse.getBody());

        // then
        assertAll(
                () -> assertThat(httpResponse.isStatusCodeEqual(StatusCode.OK)).isTrue(),
                () -> assertThat(httpResponse.isHeaderValueEqual(HeaderKey.SET_COOKIE, HeaderValue.LOGINED_TRUE_ALL_PATH)).isTrue(),
                () -> assertAll(
                        () -> assertThat(body.contains("test_id")),
                        () -> assertThat(body.contains("test_password")),
                        () -> assertThat(body.contains("test_name")),
                        () -> assertThat(body.contains("test_test@test.com"))
                )
        );
    }

    @Test
    @DisplayName("사용자 목록 조회 실패 테스트")
    void userListFail() throws IOException, URISyntaxException {
        // given
        HttpRequest httpRequest = RequestTestUtil.readTestRequest("user_list_fail.txt");

        // when
        HttpResponse httpResponse = controller.process(httpRequest);

        // then
        assertAll(
                () -> assertThat(httpResponse.isStatusCodeEqual(StatusCode.FOUND)).isTrue(),
                () -> assertThat(httpResponse.isHeaderValueEqual(HeaderKey.SET_COOKIE, HeaderValue.LOGINED_FALSE_ALL_PATH)).isTrue(),
                () -> assertThat(httpResponse.isHeaderValueEqual(HeaderKey.LOCATION, "/user/login.html")).isTrue()
        );
    }

    @ParameterizedTest
    @DisplayName("GET 요청 이외의 메서드는 Not Found 를 반환한다.")
    @CsvSource(value = {
            "POST, PUT, DELETE, PATCH"
    })
    void throw_exception_exceptGetMethod(Method method) throws IOException, URISyntaxException {
        HttpRequest httpRequest = new HttpRequest(new RequestLine(method, new Path("/user/list", new QueryString()), Protocol.ofHttp_V1_1()), new Header(), new QueryString());
        assertThat(controller.process(httpRequest)).isEqualTo(HttpResponse.notFound());
    }

    @ParameterizedTest
    @DisplayName("해당 요청에 대한 Mapping 이 일치하는지 확인한다.")
    @CsvSource(value = {
            "/user/list, true",
            "/user/lists, false",
    })
    void isMatchRequest(String path, boolean trueOrFalse) {
        HttpRequest httpRequest = new HttpRequest(new RequestLine(Method.GET, new Path(path, new QueryString()), Protocol.ofHttp_V1_1()), new Header(), new QueryString());
        assertThat(controller.isMatchPath(httpRequest)).isEqualTo(trueOrFalse);
    }
}