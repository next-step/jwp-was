package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import model.User;
import model.Users;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

public class HandlebarsTest {
    private static final Logger log = LoggerFactory.getLogger(HandlebarsTest.class);

    @Test
    void name() throws Exception {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);
        handlebars.registerHelpers(new HandlebarsHelper());

        Template template = handlebars.compile("user/list");

        Collection<User> userList = new HashSet<>();

        IntStream.rangeClosed(1,5)
                .mapToObj(x->String.valueOf(x))
                .forEach(x -> userList.add(new User("userId"+x, "password"+x, "name"+x, x+"@.x.x")));

        Users users = new Users(userList);

        String profilePage = template.apply(users);
        log.debug("ProfilePage : {}", profilePage);
    }
}
