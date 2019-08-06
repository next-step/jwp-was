package domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.HandlebarsCompiler;
import webserver.handler.Handler;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.Response;

import static support.FileSupporter.read;
import static support.FileSupporter.write;

class UserListHandlerTest {

    private Handler userListHandler;

    @BeforeEach
    void setUp() {
        userListHandler = new UserListHandler(HandlebarsCompiler.of("/templates", ".html"));
    }

    @DisplayName("로그인에 성공했다면 리스트 조회에 성공한다.")
    @Test
    void userListSuccess() throws Exception {
        try (final Response response = HttpResponse.of(write("UserList_Success_Response.txt"))) {
            userListHandler.handle(HttpRequest.of(read("UserList_Login.txt")), response);
        }
    }
    @DisplayName("로그인에 실패했다면 리스트 조회에 실패한다.")
    @Test
    void userListFail() throws Exception {
        try (final Response response = HttpResponse.of(write("UserList_Fail_Response.txt"))) {
            userListHandler.handle(HttpRequest.of(read("UserList_NotLogin.txt")), response);
        }
    }
}
