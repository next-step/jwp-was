package domain.user;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.handler.Handler;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static support.FileSupporter.read;
import static support.FileSupporter.write;

class CreateUserHandlerTest {

    private static final Handler createUserHandler = new CreateUserHandler();

    @DisplayName("유저 생성에 성공한다.")
    @Test
    void createUserSuccess() throws Exception {
        // when
        createUser();
        final User user = DataBase.findUserById("jaeyeonling");

        // then
        assertThat(user.getUserId()).isEqualTo("jaeyeonling");
    }

    public static void createUser() throws Exception {
        try (final Response response = HttpResponse.of(write("CreateUser_Response.txt"))) {
            createUserHandler.handle(HttpRequest.of(read("CreateUser_Request.txt")), response);
        }
    }
}
