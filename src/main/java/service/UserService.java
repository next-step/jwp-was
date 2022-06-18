package service;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import model.User;
import webserver.response.Response;
import webserver.response.ResponseFactory;

public class UserService {
    private UserService() {}

    public static Response createUser(User user) {
        DataBase.addUser(user);
        return ResponseFactory.createRedirect("/index.html");
    }

    public static Response getUserList(boolean loggedIn) throws IOException {
        if (!loggedIn) {
            return ResponseFactory.createRedirect("/user/login.html");
        }
        String body = renderUserList(DataBase.findAll());
        return ResponseFactory.createOK(body);
    }

    private static String renderUserList(Collection<User> users) throws IOException {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("users", users);

        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix("");
        Handlebars handlebars = new Handlebars(loader);
        Template template = handlebars.compile("/user/list.html");
        return template.apply(parameterMap);
    }
}
