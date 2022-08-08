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

        User user = new User.Builder()
                .userId("javajigi")
                .password("password")
                .name("자바지기")
                .email("javajigi@gmail.com")
                .build();

        String profilePage = template.apply(user);
        log.debug("ProfilePage : {}", profilePage);
    }

    @Test
    void userListdtos() throws Exception {
        User user1 = new User.Builder()
                .userId("javajigi")
                .password("password")
                .name("자바지기")
                .email("javajigi@gmail.com")
                .build();
        User user2 = new User.Builder()
                .userId("jiwon")
                .password("password")
                .name("지원")
                .email("jiwon@gmail.com")
                .build();
        final List<User> users = List.of(user1, user2);

        String userListPage = HandlebarsUtils.getUserListTemplate("user/list", users);
        log.debug(userListPage);
    }
}
