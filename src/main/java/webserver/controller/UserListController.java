package webserver.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import db.DataBase;
import webserver.http.header.Header;
import webserver.http.request.HttpRequest;
import webserver.http.request.requestline.Path;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.util.Map;

public class UserListController extends MethodController {
    private static final String TEMPLATE_ENGINE_USERS_KEY = "users";

    private final Handlebars handlebars;

    public UserListController(Handlebars handlebars) {
        this.handlebars = handlebars;
    }

    @Override
    public HttpResponse processGet(HttpRequest httpRequest) throws IOException {
        if (httpRequest.isLogin()) {
            Template template = getTemplate(httpRequest);
            return HttpResponse.ok(
                    Header.loginSuccessResponse(),
                    template.apply(Map.of(TEMPLATE_ENGINE_USERS_KEY, DataBase.findAll())).getBytes());
        }

        return HttpResponse.redirect("/user/login.html",
               Header.loginFailResponse()
        );
    }

    @Override
    HttpResponse processPost(HttpRequest httpRequest) {
        return HttpResponse.notFound();
    }

    private Template getTemplate(HttpRequest httpRequest) throws IOException {
        return handlebars.compile(httpRequest.getPath());
    }

    @Override
    public boolean isMatchPath(HttpRequest httpRequest) {
        return httpRequest.isPathEqual(new Path("/user/list"));
    }
}
