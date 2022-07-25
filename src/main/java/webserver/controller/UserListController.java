package webserver.controller;

import com.github.jknack.handlebars.Handlebars;
import db.DataBase;
import webserver.HttpHeaders;
import webserver.exception.ApiException;
import webserver.request.HttpMethod;
import webserver.request.HttpRequest;
import webserver.request.Path;
import webserver.response.HttpResponse;
import webserver.response.HttpStatusCode;

import java.io.IOException;
import java.util.Collections;

public class UserListController implements Controller {

    private static final Path USER_CREATE_PATH = Path.from("/user/list");
    private static final String USER_LIST_TEMPLATE = "user/list";
    private final Handlebars handlebars;

    public UserListController(Handlebars handlebars) {
        this.handlebars = handlebars;
    }

    @Override
    public boolean isMatch(HttpRequest request) {
        return request.matchMethodAndPath(HttpMethod.GET, USER_CREATE_PATH);
    }

    @Override
    public HttpResponse execute(HttpRequest request) throws IOException {
        validateLoggedIn(request);
        return HttpResponse.Builder.ok(userListBody())
                .contentType("text/html;charset=utf-8")
                .build();
    }

    private void validateLoggedIn(HttpRequest request) {
        if (isNotLoggedIn(request)) {
            throw new ApiException(HttpStatusCode.FOUND, Collections.singletonMap(HttpHeaders.LOCATION, "/user/login.html"));
        }
    }

    private boolean isNotLoggedIn(HttpRequest request) {
        return !request.cookie().contains("logined=true");
    }

    private String userListBody() throws IOException {
        return handlebars.compile(USER_LIST_TEMPLATE)
                .apply(Collections.singletonMap("users", DataBase.findAll()));
    }
}
