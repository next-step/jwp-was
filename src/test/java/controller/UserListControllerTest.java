package controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import http.StatusCode;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import sun.plugin.dom.exception.InvalidAccessException;
import testutils.FileLoader;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DisplayName("유저 리스트 컨트롤러")
class UserListControllerTest {
    private static final AbstractController CONTROLLER = new UserListController();

    @Test
    @DisplayName("/user/list post 호출시 예외 발생")
    void userListPost() throws IOException {
        HttpRequest httpRequest = HttpRequest.readRawRequest(FileLoader.load("user-list-post.txt"));
        HttpResponse httpResponse = HttpResponse.init();

        assertThatExceptionOfType(InvalidAccessException.class)
                .isThrownBy(() -> CONTROLLER.process(httpRequest, httpResponse));

        assertThat(httpResponse.getStatusCode()).isEqualTo(StatusCode.NOT_FOUND);
    }

    @Test
    @DisplayName("로그인 유저가 /user/list get 호출시 유저를 반환한다.")
    void userListGet_LoggedIn() throws IOException {
        HttpRequest httpRequest = HttpRequest.readRawRequest(FileLoader.load("user-list-get-loggedin.txt"));
        HttpResponse httpResponse = HttpResponse.init();

        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", "test");
        parameters.put("password", "1234");
        DataBase.addUser(User.init(parameters));

        CONTROLLER.process(httpRequest, httpResponse);

        assertThat(DataBase.findAll()).hasSize(1);
        assertThat(httpResponse.getStatusCode()).isEqualTo(StatusCode.OK);
        assertThat(httpResponse.getForward()).isEqualTo("user/profile");
        assertThat(httpResponse.getModels()).hasSize(1);
        assertThat((Collection)httpResponse.getModels().get("users")).hasSize(1);
        assertThat(httpResponse.getBodyLength()).isZero();
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("로그인 하지 않은 유저가 /user/list get 호출시 리다이렉트")
    void userListGet_NotLoggedIn(final String path) throws IOException {
        HttpRequest httpRequest = HttpRequest.readRawRequest(FileLoader.load(path));
        HttpResponse httpResponse = HttpResponse.init();

        CONTROLLER.process(httpRequest, httpResponse);

        assertThat(httpResponse.getStatusCode()).isEqualTo(StatusCode.REDIRECT);
        assertThat(httpResponse.getLocation()).isEqualTo("/user/login.html");
    }

    private static Stream<String> userListGet_NotLoggedIn() {
        return Stream.of(
                "user-list-get-not-loggedin.txt",
                "user-list-get.txt"
        );
    }
}
