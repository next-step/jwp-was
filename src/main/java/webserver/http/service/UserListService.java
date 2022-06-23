package webserver.http.service;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.User;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserListService extends GetService {

    @Override
    protected boolean pathMatch(HttpRequest httpRequest) {
        return httpRequest.getPath().equals("/user/list");
    }

    @Override
    public void doService(HttpRequest httpRequest, HttpResponse httpResponse) {
        boolean logined = httpRequest.isLogined();

        if (!logined) {
            httpResponse.redirect("/user/login.html");
            return;
        }

        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        try {
            Template template = handlebars.compile("user/list");
            Map<String, Object> users = new HashMap<>();
            users.put("users", DataBase.findAll());
            httpResponse.ok(template.apply(users).getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
