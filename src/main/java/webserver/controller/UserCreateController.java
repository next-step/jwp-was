package webserver.controller;

import db.DataBase;
import model.User;
import webserver.exception.ApiException;
import webserver.http.request.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.request.Path;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatusCode;

public class UserCreateController implements Controller {

    private static final Path USER_CREATE_PATH = Path.from("/user/create");

    @Override
    public boolean isMatch(HttpRequest request) {
        return request.matchMethodAndPath(HttpMethod.POST, USER_CREATE_PATH);
    }

    @Override
    public HttpResponse execute(HttpRequest request) {
        DataBase.addUser(new User(
                extractRequiredBody(request, "userId"),
                extractRequiredBody(request, "password"),
                extractRequiredBody(request, "name"),
                extractRequiredBody(request, "email")
        ));
        return HttpResponse.sendRedirect("/index.html");
    }

    private String extractRequiredBody(HttpRequest request, String property) {
        return request.bodyValue(property)
                .orElseThrow(() -> new ApiException(HttpStatusCode.BAD_REQUEST));
    }
}
