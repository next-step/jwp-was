package controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;

public class UserListController implements Controller{

    public static final String USER_LOGIN_PATH = "/user/login.html";

    @Override
    public HttpResponse process(HttpRequest request) throws Exception {

        final Cookie cookie = request.getCookie();
        final Cookie loginCookie = new Cookie("logined", "true", "/.*");

        if (validationCookie(cookie, loginCookie)) {
            return HttpResponse.success(makeUserListTemplate().getBytes());
        }
        return HttpResponse.redirect(USER_LOGIN_PATH);
    }

    private boolean validationCookie(Cookie cookie, Cookie loginCookie) {
        return cookie != null && cookie.equals(loginCookie);
    }

    private String makeUserListTemplate() throws Exception {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile("user/list");

        final Collection<User> users = DataBase.findAll();

        return template.apply(Map.of("users", users));
    }
}
