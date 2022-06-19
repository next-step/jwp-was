package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import model.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HandlebarsTest {
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

    @Test
    void list2() throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);
        handlebars.registerHelper("inc", (context, options) -> (int) context + 1);

        List<User> users = List.of(
                new User("tester1", "pw1", "테스터1", "tester1@gmail.com"),
                new User("tester2", "pw2", "테스터2", "tester2@gmail.com")
        );
        final Map<String, List<User>> params = Map.of("users", users);

        Template template = handlebars.compile("handlebars_list");
        String profilePage = template.apply(params);

        assertThat(profilePage)
                .isEqualTo("\n" +
                        "<tr>\n" +
                        "    <th scope=\"row\">1</th>\n" +
                        "    <td>tester1</td>\n" +
                        "    <td>테스터1</td>\n" +
                        "    <td>tester1@gmail.com</td>\n" +
                        "</tr>\n" +
                        "\n" +
                        "<tr>\n" +
                        "    <th scope=\"row\">2</th>\n" +
                        "    <td>tester2</td>\n" +
                        "    <td>테스터2</td>\n" +
                        "    <td>tester2@gmail.com</td>\n" +
                        "</tr>\n\n"
                );
    }
}
