package webserver.handler;

import db.DataBase;
import java.util.Collection;
import java.util.Map;
import model.User;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class UserListController implements Controller {

    @Override
    public HttpResponse handle(final HttpRequest request) {

        if (isLogin(request)) {
            final Collection<User> users = DataBase.findAll();
            final Map<String, Object> params = Map.of("users", users);

            return HttpResponse.ok("user/list", params);
        }

        return HttpResponse.redirect("/user/login.html");
    }

    private static boolean isLogin(final HttpRequest request) {
        final Object logined = request.getSession().getAttribute("logined");
        return logined != null && (boolean) logined;
    }

}
