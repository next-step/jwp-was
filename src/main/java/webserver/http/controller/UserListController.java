package webserver.http.controller;

import db.DataBase;
import model.User;
import webserver.http.Cookie;
import webserver.http.controller.utils.SessionUtils;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.session.HttpSession;
import webserver.http.template.handlebars.HandlebarsTemplateLoader;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public class UserListController extends AbstractController {

    @Override
    protected void doGet(final HttpRequest request, final HttpResponse response) throws IOException {
        final HttpSession session = request.getSession();
        final User loginedUser = SessionUtils.getUserOrNull(session);

        if (loginedUser == null) {
            response.responseRedirect("/index.html");
            return;
        }

        final Collection<User> users = DataBase.findAll();
        final Map<String, Object> params = Map.of("users", users);

        final HandlebarsTemplateLoader handlebarsTemplateLoader = new HandlebarsTemplateLoader();
        final String page = handlebarsTemplateLoader.load("user/list", params);

        response.setContentType("text/html;charset=utf-8");
        response.setBody(page);
        response.responseOK();
    }
}
