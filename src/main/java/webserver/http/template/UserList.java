package webserver.http.template;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class UserList {
    private static final Logger logger = LoggerFactory.getLogger(UserList.class);
    private final List<User> users;

    public UserList(List<User> users) {
        if (CollectionUtils.isEmpty(users)) {
            throw new IllegalArgumentException();
        }
        this.users = users;
    }

    public String generateUserListTemplate() {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");

        try {
            Template template = new Handlebars(loader).compile("user/list");
            return Objects.requireNonNull(template).apply(this.users);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return null;
    }
}
