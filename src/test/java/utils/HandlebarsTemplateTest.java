package utils;

import java.util.Arrays;
import java.util.Collections;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class HandlebarsTemplateTest {

    private static final Logger log = LoggerFactory.getLogger(HandlebarsTemplateTest.class);

    @DisplayName("user/profile 페이지를 Handlebars template 써서 로드에 성공한다.")
    @Test
    void name() throws Exception {
        User user = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");
        String profilePage = HandlebarsTemplate.create("/templates", ".html", "user/profile")
            .apply(user);

        log.debug("ProfilePage : {}", profilePage);
    }

    @DisplayName("user/list 페이지를 Handlebars template 써서 로드에 성공한다.")
    @Test
    void list() throws Exception {
        User user1 = new User("test1", "test1", "test1", "test@gmail.com");
        User user2 = new User("test2", "test2", "test2", "test@gmail.com");
        User user3 = new User("test3", "test3", "test3", "test@gmail.com");

        String profilePage = HandlebarsTemplate.create("/templates", ".html", "user/list")
            .apply(Collections.singletonMap("users", Arrays.asList(user1, user2, user3)));

        log.debug("ProfilePage : {}", profilePage);
    }
}
