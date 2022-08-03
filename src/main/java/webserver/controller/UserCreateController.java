package webserver.controller;

import db.DataBase;
import exception.NotFoundException;
import http.request.Protocol;
import model.User;
import webserver.HttpHeader;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatusCode;
import webserver.response.StatusLine;

import java.util.Map;

public class UserCreateController implements Controller {

    @Override
    public HttpResponse service(HttpRequest request) {
        DataBase.addUser(new User(
                extractRequireBody(request, "userId"),
                extractRequireBody(request, "password"),
                extractRequireBody(request, "name"),
                extractRequireBody(request, "email")
                )
        );
        return HttpResponse.of(
                StatusLine.of(Protocol.from("HTTP/1.1"), HttpStatusCode.FOUND),
                HttpHeader.from(Map.of(HttpHeader.LOCATION, "/index.html"))
        );
    }

    private String extractRequireBody(HttpRequest request, String key) {
        return request.getBody(key)
                .orElseThrow(() -> new NotFoundException(HttpStatusCode.BAD_REQUEST));
    }
}
