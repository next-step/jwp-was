package controller;

import com.github.jknack.handlebars.Handlebars;
import db.DataBase;
import model.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
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
    public HttpResponse execute(HttpRequest request) throws IOException, URISyntaxException {
        if (!isLogin(request)) {
            return HttpResponse.found("/user/login.html");
        }

        Collection<User> users = DataBase.findAll();

        return HttpResponse.ok(
                ResponseHeader.of(Map.of(
                        HttpHeaders.CONTENT_TYPE, "text/html;charset=utf-8")),
                handlebars.compile(USER_LIST_TEMPLATE)
                        .apply(Map.of("users", users))
                        .getBytes(StandardCharsets.UTF_8)
        );
    }

    private boolean isLogin(HttpRequest request) {
        return request.isLogin();
    }
}
