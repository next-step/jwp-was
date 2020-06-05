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
    public UserListController() {
        super("/user/list");
    }

    @Override
    protected HttpResponse get(HttpRequest request) throws IOException {
        if (!LoginUtil.isLogedIn(request)) {
            return HttpResponse.redirect("/user/login.html");
        }

        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile("user/profile");

        Collection<User> users = DataBase.findAll();


        System.out.println(users.size());


        Map<String, Object> map = new HashMap<>();
        map.put("users", users);
        String profilePage = template.apply(map);

        return HttpResponse.ok(ContentType.HTML, profilePage.getBytes());
    }

    @Override
    protected HttpResponse post(HttpRequest request) {
        throw new InvalidAccessException("Post not support");
    }
}
