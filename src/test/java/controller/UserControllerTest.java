package controller;

import db.DataBase;
import http.request.QueryString;
import http.response.HttpResponse;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserControllerTest {

    @DisplayName("유저를 생성하면 DB에 저장된다")
    @Test
    void createUser() {
        UserController userController = new UserController();
        userController.createUser(
                new QueryString("userId=javajigi&password=password&name=JaeSung&email=test@com"),
                new HttpResponse(createDataOutputStream()));
        User user = new User(new QueryString("userId=javajigi&password=password&name=JaeSung&email=test@com"));

        List<User> users = new ArrayList<>(DataBase.findAll());
        assertThat(users).hasSize(1);
        assertThat(users.get(0)).isEqualTo(user);
    }

    private DataOutputStream createDataOutputStream() {
        return new DataOutputStream(
                new OutputStream() {
                    @Override
                    public void write(int b) throws IOException {
                    }
                });
    }
}
