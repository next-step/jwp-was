import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import model.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
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

        Template template = handlebars.compile("user/list");

        User user = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");
        User dowon = new User("dowon", "dowon", "도원", "dowon@gmail.com");

        List<User> users = Arrays.asList(user, dowon);
        Map<String, Object> model = new HashMap<>();
        model.put("users", users);
        String profilePage = template.apply(model);
        assertThat(profilePage).contains("자바지기", "도원");
        log.debug("ProfilePage : {}", profilePage);
    }
}