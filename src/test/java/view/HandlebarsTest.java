package view;

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
import java.util.Map;

public class HandlebarsTest {

    private static final Logger log = LoggerFactory.getLogger(HandlebarsTest.class);

    @Test
    void handlebar_학습_테스트() throws Exception {
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
    void handlebar_학습_테스트_유저_여러명_확인() throws Exception {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile("user/list");

        User javajigi = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");
        User hkkang = new User("hkkang", "password", "구구", "hkkang@woowahan.com");

        Map<String, Object> model = new HashMap<>();
        model.put("users", Arrays.asList(javajigi, hkkang));
        String listPage = template.apply(model);
        log.debug("ListPage : {}", listPage);
    }
}
