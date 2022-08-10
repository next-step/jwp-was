package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class HandlebarsTest {
    private static final Logger log = LoggerFactory.getLogger(HandlebarsTest.class);

    @Test
    void 템플릿적용_출력_확인() throws Exception {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile("/user/list");

        User user = new User("asdfasdfasdfasdfadsfadsfasdfs", "password", "asdfasdfasdfasdfadsfadsfasdfs", "asdfasdfasdfasdfadsfadsfasdfs");
        DataBase.addUser(user);
        final Collection<User> users = DataBase.findAll();

        Map<String, Collection<User>> map = new HashMap<>();
        map.put("users", users);

        String profilePage = template.apply(map);
        log.debug("ProfilePage : {}", profilePage);
    }
}
