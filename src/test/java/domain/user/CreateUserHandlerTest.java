package domain.user;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webserver.http.response.HttpResponse;
import webserver.http.request.HttpRequest;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;

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
        final HttpRequest request = HttpRequest.of(new ByteArrayInputStream(("POST /user/create HTTP/1.1 \r\n" +
                "Content-Length: 77\r\n\r\n" +
                "userId=jaeyeonling&password=password&name=jaeyeon&email=jaeyeonling@gmail.com").getBytes()));

        // when
        createUserHandler.handle(request, HttpResponse.of(new OutputStream() {
            @Override
            public void write(int ignore) { }
        }));

        final User user = DataBase.findUserById("jaeyeonling");

        // then
        assertThat(user.getUserId()).isEqualTo("jaeyeonling");
    }
}
