package controller;

import db.DataBase;
import http.HttpRequest;
import http.response.HttpResponse;
import http.StatusCode;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sun.plugin.dom.exception.InvalidAccessException;
import testutils.FileLoader;

import java.io.IOException;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DisplayName("유저 컨트롤러")
class UserControllerTest {
    private static final AbstractController USER_CONTROLLER = new UserController();

    @BeforeEach
    void clear() {
        DataBase.deleteAll();
    }

    @Test
    @DisplayName("/user/create 호출시 예외 발생")
    void create() throws IOException {
        HttpRequest httpRequest = HttpRequest.readRawRequest(FileLoader.load("user-create-get.txt"));
        HttpResponse httpResponse = HttpResponse.init();

        assertThatExceptionOfType(InvalidAccessException.class)
                .isThrownBy(() -> USER_CONTROLLER.process(httpRequest, httpResponse));

        assertThat(httpResponse.getStatusCode()).isEqualTo(StatusCode.NOT_FOUND);
    }

    @Test
    @DisplayName("/user/create POST 호출시 유저를 메모리에 저장한다.")
    void createUsingPost() throws IOException {
        HttpRequest httpRequest = HttpRequest.readRawRequest(FileLoader.load("user-create-post.txt"));
        HttpResponse httpResponse = HttpResponse.init();
        Collection<User> users = DataBase.findAll();

        assertThat(users).isEmpty();
        USER_CONTROLLER.process(httpRequest, httpResponse);

        assertThat(DataBase.findAll()).hasSize(1);
        assertThat(httpResponse.getStatusCode()).isEqualTo(StatusCode.REDIRECT);
        assertThat(httpResponse.getLocation()).isEqualTo("/index.html");
    }
}