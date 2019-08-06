package webserver;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webserver.http.ModelAndView;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class ViewResolverTest {
    private User user;
    @BeforeEach
    void setUp() {
        user = new User(
                "dave",
                "password",
                "Taehyun",
                "aa@aaa.com"
        );
        DataBase.addUser(user);
    }

    @Test
    void mapping() throws IOException {
        ModelAndView modelAndView = new ModelAndView("/user/list");
        modelAndView.setAttribute("users", DataBase.findAll());

        assertThat(ViewResolver.mapping(modelAndView)).contains(user.getUserId());
        assertThat(ViewResolver.mapping(modelAndView)).contains(user.getName());
        assertThat(ViewResolver.mapping(modelAndView)).contains(user.getEmail());
    }
}
