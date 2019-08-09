package webserver.service;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.UserService;
import webserver.ComponentFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceTest {

    @DisplayName("add and get user service")
    @Test
    void test() {
        User user = new User("jun", "password", "hyunjun", "homelus@daum.net");

        UserService userService = ComponentFactory.userService;
        userService.add(user);
        User jun = userService.get(user.getUserId());
        assertThat(user).isEqualTo(jun);
    }

}
