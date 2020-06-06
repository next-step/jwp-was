package controller;

import db.DataBase;
import http.request.QueryString;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.ViewHandler;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserControllerTest {

    @DisplayName("유저를 생성하면 DB에 저장된다")
    @Test
    void createUser() {
        UserController userController = new UserController();
        userController.createUser(new QueryString("userId=javajigi&password=password&name=JaeSung&email=test@com"), new ViewHandler());
        User user = new User(new QueryString("userId=javajigi&password=password&name=JaeSung&email=test@com"));

        List<User> users = new ArrayList<>(DataBase.findAll());
        assertThat(users).hasSize(1);
        assertThat(users.get(0)).isEqualTo(user);
    }
}
