package utils;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class HandlebarsTest {

    private static final Logger log = LoggerFactory.getLogger(HandlebarsTest.class);

    @Test
    void name() throws Exception {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile("user/profile");

        User user = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");
        String profilePage = template.apply(user);
        log.debug("ProfilePage : {}", profilePage);
    }

    @DisplayName("사용자목록 template")
    @Test
    void user_list_template() throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);
        handlebars.registerHelper("inc", (context, options) -> (int) context + 1);

        Template template = handlebars.compile("user/list");

        User user = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");

        final List<User> users = List.of(user);
        final Map<String, List<User>> param = Map.of("users", users);

        String profilePage = template.apply(param);
        assertThat(profilePage).isEqualTo(
            "\n"
                + "<tr>\n"
                + "  <td>1</td>\n"
                + "  <td>자바지기</td>\n"
                + "</tr>\n"
                + "\n"
        );
    }
}
