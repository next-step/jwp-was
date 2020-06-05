package controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import http.ContentType;
import http.HttpRequest;
import http.HttpResponse;
import model.User;
import sun.plugin.dom.exception.InvalidAccessException;
import utils.LoginUtil;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserListController extends Controller {
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
    protected HttpResponse get(final HttpRequest request) throws IOException {
        if (!LoginUtil.isLogedIn(request)) {
            return HttpResponse.redirect("/user/login.html");
        }

        Template template = HANDLEBARS.compile("user/profile");

        Collection<User> users = DataBase.findAll();

        Map<String, Object> map = new HashMap<>();
        map.put("users", users);
        String profilePage = template.apply(map);

        return HttpResponse.ok(ContentType.HTML, profilePage.getBytes());
    }

    @Override
    protected HttpResponse post(final HttpRequest request) {
        throw new InvalidAccessException("Post not support");
    }
}
