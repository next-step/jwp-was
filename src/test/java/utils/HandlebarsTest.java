package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import model.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HandlebarsTest {
    private static final Logger log = LoggerFactory.getLogger(HandlebarsTest.class);

    @Test
    void name() throws Exception {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile("user/list");

        List<User> users = new ArrayList<>();
        User user = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");
        User user1 = new User("javajigi1", "password", "자바지기1", "javajigi@gmail.com");

        users.add(user);
        users.add(user1);

        String profilePage = template.apply(users);
        log.debug("ProfilePage : {}", profilePage);
    }
}
