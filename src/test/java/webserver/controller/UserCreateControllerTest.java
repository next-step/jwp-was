package webserver.controller;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.HttpHeader;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatusCode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("회원가입 컨트롤러 테스트")
class UserCreateControllerTest {

    private String testDirectory = "./src/test/resources/";
    private HttpResponse response;

    @BeforeEach
    void initEach() throws IOException {
        HttpRequest request = HttpRequest.from(new FileInputStream(new File(testDirectory + "Http_POST.txt")));
        UserCreateController controller = new UserCreateController();
        response = controller.service(request);
    }

    @DisplayName("회원 가입 테스트 - POST")
    @Test
    void createUser_POST() {
        assertThat(DataBase.findUserById("javajigi")).isNotNull();
    }

    @DisplayName("회원 가입 정보 DB 테스트")
    @Test
    void userInfo() {
        User user = DataBase.findUserById("javajigi");

        assertAll(
                () -> assertThat(user.getUserId()).isEqualTo("javajigi"),
                () -> assertThat(user.getPassword()).isEqualTo("password"),
                () -> assertThat(user.getName()).isEqualTo("JaeSung")
        );
    }

    @DisplayName("회원가입시 index.html로 리다이랙트")
    @Test
    void redirect() {
        assertAll(
                () -> assertThat(response.getHttpStatusCode()).isEqualTo(HttpStatusCode.FOUND),
                () -> assertThat(response.getResponseHeader(HttpHeader.LOCATION)).contains("/index.html")
        );
    }
}
