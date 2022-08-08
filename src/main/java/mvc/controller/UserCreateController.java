package mvc.controller;

import db.DataBase;
import exception.NotFoundException;
import http.request.protocol.Protocol;
import mvc.model.User;
import http.HttpHeader;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpStatusCode;
import http.response.StatusLine;

import java.util.Map;

public class UserCreateController extends AbstractController {

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        DataBase.addUser(new User(
                extractRequireBody(request, "userId"),
                extractRequireBody(request, "password"),
                extractRequireBody(request, "name"),
                extractRequireBody(request, "email")
                )
        );
        response.buildResponse(
                StatusLine.of(Protocol.from("HTTP/1.1"), HttpStatusCode.CREATED),
                HttpHeader.from(Map.of(HttpHeader.LOCATION, "/index.html"))
        );
    }

    private String extractRequireBody(HttpRequest request, String key) {
        return request.getBody(key)
                .orElseThrow(() -> new NotFoundException(HttpStatusCode.BAD_REQUEST));
    }
}
