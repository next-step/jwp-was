package controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.Users;
import utils.HandlebarsHelper;
import webserver.controller.AbstractController;

import java.io.IOException;

public class UserListController extends AbstractController {

    private UserListController() {}

    private static class Singleton {
        private static final UserListController instance = new UserListController();
    }

    public static UserListController getInstance() {
        return Singleton.instance;
    }

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (isLogined(httpRequest)) {
            final Users users = new Users(DataBase.findAll());

            final TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("/templates");
            loader.setSuffix(".html");

            final Handlebars handlebars = new Handlebars(loader);
            handlebars.registerHelpers(new HandlebarsHelper());

            try {
                final Template template = handlebars.compile("user/list");
                byte[] htmlFile = template.apply(users).getBytes();
                httpResponse.response200HTML(htmlFile);
            } catch (IOException e) {
                httpResponse.response302("/index.html");
            }

        } else {
            httpResponse.response302("/index.html");
        }
    }

    private boolean isLogined(HttpRequest httpRequest) {
        final String logined = httpRequest.getCookie("logined");
        return "true".equals(logined);
    }

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
    }

}
