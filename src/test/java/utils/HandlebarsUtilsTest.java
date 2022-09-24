package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class HandlebarsUtilsTest {

    @BeforeEach
    void setUp() {
        DataBase.deleteAll();

        User user = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");
        DataBase.addUser(user);
    }

    @Test
    void load() throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader("/templates", ".html");
        Handlebars handlebars = new Handlebars(loader);

        Template tempalte = handlebars.compile("/user/list");

        Collection<User> users = DataBase.findAll();
        String profilePage = tempalte.apply(Collections.singletonMap("users", users));

        System.out.println(profilePage);
        assertThat(profilePage).isNotEmpty();
    }
}
