package webserver.controller;

import com.github.jknack.handlebars.Handlebars;
import db.DataBase;
import webserver.http.Header;
import webserver.http.request.HttpRequest;
import webserver.http.request.requestline.Method;
import webserver.http.request.requestline.Path;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class UserListController implements Controller {
    private static final String TEMPLATE_ENGINE_USERS_KEY = "users";

    private final Handlebars handlebars;

    public UserListController(Handlebars handlebars) {
        this.handlebars = handlebars;
    }

    @Override
    public HttpResponse process(HttpRequest httpRequest) throws IOException, URISyntaxException {
        if (httpRequest.isLogin()) {
            return HttpResponse.ok(
                    Header.loginSuccessResponse(),
                    handlebars.compile(httpRequest.getPath()).apply(Map.of(TEMPLATE_ENGINE_USERS_KEY, DataBase.findAll())).getBytes());
        }

        return HttpResponse.redirect("/user/login.html",
               Header.loginFailResponse()
        );
    }

    @Override
    public boolean isMatchRequest(HttpRequest httpRequest) {
        return httpRequest.isMethodEqual(Method.GET) && httpRequest.isPathEqual(new Path("/user/list"));
    }
}
