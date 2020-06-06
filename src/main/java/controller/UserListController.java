package controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;
import utils.LoginUtil;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserListController extends AbstractController {
    private static final Handlebars HANDLEBARS;

    static {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        HANDLEBARS = new Handlebars(loader);
    }

    public UserListController() {
        super("/user/list");
    }

    @Override
    protected void get(final HttpRequest request, final HttpResponse httpResponse) throws IOException {
        if (!LoginUtil.isLoggedIn(request)) {
            httpResponse.sendRedirect("/user/login.html");
            return;
        }

        Template template = HANDLEBARS.compile("user/profile");

        Collection<User> users = DataBase.findAll();

        Map<String, Object> map = new HashMap<>();
        map.put("users", users);
        String profilePage = template.apply(map);

        httpResponse.updateBody(profilePage.getBytes());
    }
}
