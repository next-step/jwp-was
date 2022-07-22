package webserver.controller;

import db.DataBase;
import model.User;
import webserver.HttpHeaders;
import webserver.exception.ApiException;
import webserver.request.HttpMethod;
import webserver.request.HttpRequest;
import webserver.request.Path;
import webserver.response.HttpResponse;
import webserver.response.HttpStatusCode;
import webserver.response.ResponseHeader;

import java.util.Map;

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
        return HttpResponse.of(
                HttpStatusCode.FOUND,
                ResponseHeader.from(Map.of(HttpHeaders.LOCATION, "/index.html"))
        );
    }

    private String extractRequiredBody(HttpRequest request, String property) {
        return request.bodyValue(property)
                .orElseThrow(() -> new ApiException(HttpStatusCode.BAD_REQUEST));
    }
}
