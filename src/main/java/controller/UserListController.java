package controller;

import com.github.jknack.handlebars.Handlebars;
import db.DataBase;
import model.*;

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
    public boolean matchHttpMethodAndPath(HttpRequest request) {
        return request.isMatch(HttpMethod.GET, path);
    }

    @Override
    public HttpResponse execute(HttpRequest request) throws IOException, URISyntaxException {
        if (!isLogin(request)) {
            return HttpResponse.of(
                    HttpStatusCode.FOUND,
                    ResponseHeader.of(Map.of(
                            HttpHeaders.LOCATION, "/user/login.html"))
            );
        }

        Collection<User> users = DataBase.findAll();
        return HttpResponse.of(
                HttpStatusCode.OK,
                ResponseHeader.of(Map.of(
                        HttpHeaders.CONTENT_TYPE, "text/html;charset=utf-8")),
                handlebars.compile(USER_LIST_TEMPLATE)
                        .apply(Map.of("users", users))
        );
    }

    private boolean isLogin(HttpRequest request) {
        return request.isLogin();
    }
}
