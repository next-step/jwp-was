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
import webserver.http.request.HttpRequest;
import webserver.http.request.requestline.Method;
import webserver.http.request.requestline.Path;
import webserver.http.request.requestline.Protocol;
import webserver.http.request.requestline.QueryString;
import webserver.http.request.requestline.RequestLine;
import webserver.http.request.requestline.Version;
import webserver.http.response.HttpResponse;
import webserver.http.response.statusline.StatusCode;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
                () -> assertThat(httpResponse.isStatusCodeEqual(StatusCode.OK)).isEqualTo(true),
                () -> assertThat(httpResponse.isHeaderValueEqual("Set-Cookie", "logined=true; Path=/")).isEqualTo(true),
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
                () -> assertThat(httpResponse.isStatusCodeEqual(StatusCode.FOUND)).isEqualTo(true),
                () -> assertThat(httpResponse.isHeaderValueEqual("Set-Cookie", "logined=false; Path=/")).isEqualTo(true),
                () -> assertThat(httpResponse.isHeaderValueEqual("Location", "/user/login.html")).isEqualTo(true)
        );
    }

    @ParameterizedTest
    @DisplayName("해당 요청에 대한 Mapping 이 일치하는지 확인한다.")
    @CsvSource(value = {
            "GET, /user/list, true",
            "GET, /user/lists, false",
            "POST, /user/list, false",
            "POST, /user/lists, false"
    })
    void isMatchRequest(Method method, String path, boolean trueOrFalse) {
        HttpRequest httpRequest = new HttpRequest(new RequestLine(method, new Path(path, new QueryString()), new Protocol("HTTP", Version.ONE_ONE)), new Header(), new QueryString());
        assertThat(controller.isMatchRequest(httpRequest)).isEqualTo(trueOrFalse);
    }
}