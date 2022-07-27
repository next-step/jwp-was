package webserver.template;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class UserList {
    private static final Logger logger = LoggerFactory.getLogger(UserList.class);
    private final List<User> users;

    public UserList(List<User> users) {
        if (users == null) {
            throw new IllegalArgumentException("잘못된 사용자 목록");
        }
        this.users = users;
    }

    public String generateUserListTemplate() {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = null;
        try {
            template = handlebars.compile("user/list");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        String profilePage = null;
        try {
            profilePage = template.apply(this.users);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return profilePage;
    }
}
