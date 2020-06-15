package view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import model.User;
import model.Users;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TemplateViewTest {
    private static final Logger log = LoggerFactory.getLogger(TemplateViewTest.class);

    @Test
    void read_template() throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        handlebars.registerHelper("increaseOne", (Helper<Integer>) (context, options) -> context + 1);
        Template template = handlebars.compile("user/test");

        Users users = new Users();
        User user1 = new User("s1", "password1", "name1", "s1@gmail.com");
        User user2 = new User("s2", "password2", "name2", "s2@gmail.com");
        users.addUser(user1);
        users.addUser(user2);
        String profilePage = template.apply(users);

        log.debug("ListPage : \r\n{} ", profilePage);
    }

}