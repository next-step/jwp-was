package controller;

import com.github.jknack.handlebars.Handlebars;
import db.DataBase;
import model.User;
import webserver.http.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.Path;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;

public class UserListController implements Controller {

    private static final Path path = Path.of("/user/list");
    private static final String USER_LIST_TEMPLATE = "user/list";
    private final Handlebars handlebars;

    public UserListController(Handlebars handlebars) {
        this.handlebars = handlebars;
    }

    @Override
    public boolean match(HttpRequest request) {
        return request.isMatch(HttpMethod.GET, path);
    }

    @Override
    public HttpResponse execute(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        if (!isLogin(request)) {
            return response.sendRedirect("/user/login.html");
        }

        Collection<User> users = DataBase.findAll();
        String body = handlebars.compile(USER_LIST_TEMPLATE)
                .apply(Map.of("users", users));

        return response.forwardBody(body);
    }

    private boolean isLogin(HttpRequest request) {
        return request.isLogin();
    }
}
