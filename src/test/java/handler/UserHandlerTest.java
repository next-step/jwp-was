package handler;


import static org.assertj.core.api.Assertions.assertThat;

import db.DataBase;
import http.request.HttpRequest;
import model.User;
import org.junit.jupiter.api.Test;

class UserHandlerTest {

    @Test
    void create() {
        HttpRequest httpRequest = HttpRequest
            .of("GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1");

        UserHandler handler = new UserHandler();
        handler.create(httpRequest);

        User user = DataBase.findUserById("javajigi");
        assertThat(user.getUserId()).isEqualTo("javajigi");
    }

}
