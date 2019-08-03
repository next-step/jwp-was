package webserver.http.handler;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webserver.HttpResponse;
import webserver.http.HttpRequest;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;

class CreateUserHandlerTest {

    private CreateUserHandler createUserHandler;

    @BeforeEach
    void setUp() {
        createUserHandler = new CreateUserHandler();
    }

    @Test
    void createUser() throws Exception {
        // given
        final HttpRequest request = HttpRequest.of(new ByteArrayInputStream(("GET /create?userId=jaeyeonling&password=password&" +
                "name=jaeyeon&email=jaeyeonling@gmail.com HTTP/1.1 \n\n").getBytes()));
        final HttpResponse response = HttpResponse.of(System.out);

        // when
        createUserHandler.handle(request, response);
        final User user = DataBase.findUserById("jaeyeonling");

        // then
        assertThat(user).isNotNull();
    }
}
