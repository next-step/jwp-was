package controller;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webserver.Request;
import webserver.Response;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class CreatUserControllerTest {

    @BeforeEach
    void setup() {
        DataBase.clear();
    }

    @Test
    void serving() throws IOException, URISyntaxException {
        Request request = new Request("GET /create?" +
                "userId=javajigi&" +
                "password=password&" +
                "name=JaeSung&" +
                "email=javajigi@slipp.net" +
                " HTTP/1.1");

        Controller controller = new CreatUserController();

        Response response = controller.serving(request);

        assertThat(response.getContentType()).isEqualTo("text/html;charset=utf-8");
        assertThat(response.getPath()).isEqualTo("/index.html");

        User user = DataBase.findUserById("javajigi");

        assertThat(user.getUserId()).isEqualTo("javajigi");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.getName()).isEqualTo("JaeSung");
        assertThat(user.getEmail()).isEqualTo("javajigi@slipp.net");
    }

}