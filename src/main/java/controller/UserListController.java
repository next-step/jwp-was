package controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import http.common.ContentType;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.Users;
import utils.HandlebarsHelper;
import webserver.controller.AbstractController;
import webserver.session.HttpSession;

import java.io.IOException;

public class UserListController extends AbstractController {

    private UserListController() {
    }

    private static class Singleton {
        private static final UserListController instance = new UserListController();
    }

    public static UserListController getInstance() {
        return Singleton.instance;
    }

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (!isAuthenticated(httpRequest.getSession(false))) {
            httpResponse.sendRedirect("/index.html");
            return;
        }
        final Users users = new Users(DataBase.findAll());

        final TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");

        final Handlebars handlebars = new Handlebars(loader);
        handlebars.registerHelpers(new HandlebarsHelper());

        try {
            final Template template = handlebars.compile("user/list");
            String htmlFile = template.apply(users);
            httpResponse.setContentType(ContentType.TEXT_HTML_UTF_8);
            httpResponse.setBody(htmlFile);
        } catch (IOException e) {
            httpResponse.setBody("/index.html");
        }
    }

    private boolean isAuthenticated(HttpSession session) {
        if (session == null) {
            return false;
        }

        Object isAuthenticatedObj = session.getAttribute("isAuthenticated");

        if (isAuthenticatedObj == null) {
            return false;
        }

        return (boolean) isAuthenticatedObj;
    }

}
