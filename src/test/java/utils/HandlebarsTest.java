package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import model.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HandlebarsTest {
    private static final Logger log = LoggerFactory.getLogger(HandlebarsTest.class);

    @Test
    void name() throws Exception {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile("user/profile");

        User user1 = new User("1", "password", "자바지기", "javajigi@gmail.com");
        User user2 = new User("2", "password", "자바지기", "javajigi@gmail.com");
        User user3 = new User("3", "password", "자바지기", "javajigi@gmail.com");
        List<User> users = List.of(user1, user2, user3);
        String profilePage = template.apply(users);
        log.debug("ProfilePage : {}", profilePage);
    }
}
